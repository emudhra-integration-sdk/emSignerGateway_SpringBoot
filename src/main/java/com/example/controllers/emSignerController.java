package com.example.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.utils.DataEntity;
import com.example.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class emSignerController {

	private static final Map<String, String> sessionKeyStore = new ConcurrentHashMap<>();

	@PostMapping("/getEmSignerParams")
	public ResponseEntity<?> getEmSignerParams(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();

		try {
			byte[] skey = Utilities.GenerateSessionKey();
			String b4Skey = Base64.getEncoder().encodeToString(skey);
			String txnID = UUID.randomUUID().toString().replace("-", "");

			String requestRowData = Utilities.getStringFromInputStream(request.getInputStream());
			if (requestRowData.isEmpty()) {
				throw new Exception("Request cannot be empty.");
			}

			DataEntity dataEntity = gson.fromJson(requestRowData, DataEntity.class);
			if (dataEntity == null) {
				throw new Exception("Invalid request.");
			}

			String requestJson = gson.toJson(dataEntity);
			System.out.println("Received DataEntity:\n" + requestJson);

			byte[] encryptSkeyPkey = Utilities.EncryptUsingPublicKey(skey);
			String b4encryptSkeyPkey = Base64.getEncoder().encodeToString(encryptSkeyPkey);

			byte[] encryptJsonSkey = Utilities.EncryptUsingSessionKey(skey, requestJson.getBytes());
			String b4encryptJsonSkey = Base64.getEncoder().encodeToString(encryptJsonSkey);

			byte[] hash = Utilities.GenerateSha256Hash(requestJson.getBytes());
			byte[] encryptHash = Utilities.EncryptUsingSessionKey(skey, hash);
			String b4encryptHashSkey = Base64.getEncoder().encodeToString(encryptHash);

			jsonObject.addProperty("Parameter1", b4encryptSkeyPkey);
			jsonObject.addProperty("Parameter2", b4encryptJsonSkey);
			jsonObject.addProperty("Parameter3", b4encryptHashSkey);
			jsonObject.addProperty("SessionKey", b4Skey);
			jsonObject.addProperty("Status", true);

			System.out.println("Generated Session Key:\n" + b4Skey);
			System.out.println("Reference Number:\n" + dataEntity.getReferenceNumber());

			 
			sessionKeyStore.put(dataEntity.getReferenceNumber(), b4Skey);

			return new ResponseEntity<>(gson.toJson(jsonObject), HttpStatus.OK);

		} catch (Exception e) {
			jsonObject.addProperty("Status", false);
			jsonObject.addProperty("ErrorMessage", e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.contentType(MediaType.APPLICATION_JSON)
					.body(gson.toJson(jsonObject));
		}
	}

//	@PostMapping("/success")
//	public void success(HttpServletRequest request, HttpServletResponse response) throws Exception {
//	    System.out.println("Success callback received.");
//
//	    Map<String, String> data = getRequestParametersMap(request.getParameterMap());
//	    if (data.isEmpty()) {
//	        throw new Exception("Request cannot be empty.");
//	    }
//
//	    System.out.println("Webhook response --------:");
//	    data.forEach((k, v) -> System.out.println(k + " = " + v));
//
//	    String referenceNumber = data.get("Referencenumber");
//	    if (referenceNumber == null || referenceNumber.isEmpty()) {
//	        throw new IllegalArgumentException("Missing Referencenumber in callback.");
//	    }
//
//	    String encodedSessionKey = sessionKeyStore.get(referenceNumber);
//	    if (encodedSessionKey == null || encodedSessionKey.isEmpty()) {
//	        throw new IllegalArgumentException("SessionKey not found for Referencenumber: " + referenceNumber);
//	    }
//
//	    if ("Success".equalsIgnoreCase(data.get("ReturnStatus"))) {
//	        String returnValues = data.get("Returnvalue");
//
//	       
//	        String signedPdf = getPDF(data, returnValues, encodedSessionKey);
//	        
//	        sessionKeyStore.put("signedPdf_" + referenceNumber,signedPdf);
// 
//	        
//	        String redirectUrl = "http://localhost:5173/DownloadPDF?ref=" + referenceNumber;
//
//	        response.sendRedirect(redirectUrl);
//
// 
//	    } else {
//	        System.out.println("ReturnStatus not successful: " + data.get("ReturnStatus"));
//	    }
//	}
	@PostMapping("/success")
	public ResponseEntity<Void> success(@RequestParam Map<String,String> data) throws Exception {

	    System.out.println("===== Success callback received =====");

	    // Print all incoming parameters (VERY IMPORTANT for debugging)
	    data.forEach((k,v) -> System.out.println(k + " = " + v));

	    // IMPORTANT: Check correct key name from emSigner
	    String referenceNumber = data.get("ReferenceNumber");
	    System.out.println(referenceNumber);

	    if(referenceNumber == null || referenceNumber.isEmpty()) {

	        // Sometimes wrong casing comes — try fallback
	        referenceNumber = data.get("Referencenumber");
	    }

	    if(referenceNumber == null || referenceNumber.isEmpty()) {

	        System.out.println("❌ ReferenceNumber missing — ignoring callback safely.");
	        return ResponseEntity.ok().build();
	    }

	    System.out.println("ReferenceNumber received: " + referenceNumber);

	    String encodedSessionKey = sessionKeyStore.get(referenceNumber);

	    if(encodedSessionKey == null) {

	        System.out.println("❌ SessionKey not found for reference: " + referenceNumber);
	        return ResponseEntity.ok().build();
	    }

	    String returnStatus = data.get("ReturnStatus");

	    if("Success".equalsIgnoreCase(returnStatus)) {

	        String returnValues = data.get("Returnvalue");

	        if(returnValues == null || returnValues.isEmpty()) {

	            System.out.println("⚠ Returnvalue empty — ignoring.");
	            return ResponseEntity.ok().build();
	        }

	        // Generate signed PDF
	        String signedPdf = getPDF(data, returnValues, encodedSessionKey);

	        // Store signed PDF
	        sessionKeyStore.put("signedPdf_" + referenceNumber, signedPdf);

	        // Redirect to React page
	        String redirectUrl = "http://localhost:5173/DownloadPDF?ref=" + referenceNumber;

	        HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(new java.net.URI(redirectUrl));

	        System.out.println("✅ Redirecting to React: " + redirectUrl);

	        return new ResponseEntity<>(headers, HttpStatus.FOUND);
	    }

	    System.out.println("ReturnStatus NOT success: " + returnStatus);

	    return ResponseEntity.ok().build();
	}

	 

	@PostMapping("/failed")
	public ResponseEntity<?> failed(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Failed callback received.");
		String requestRowData = Utilities.getStringFromInputStream(request.getInputStream());
		System.out.println(requestRowData);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/canceled")
	public ResponseEntity<?> canceled(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Canceled callback received.");
		String requestRowData = Utilities.getStringFromInputStream(request.getInputStream());
		System.out.println(requestRowData);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private Map<String, String> getRequestParametersMap(Map<String, String[]> map) {
		Map<String, String> RequestParameters = new HashMap<>();
		for (String parameterName : map.keySet()) {
			String[] values = map.get(parameterName);
			if (values != null && values.length > 0) {
				RequestParameters.put(parameterName, values[0]);
			} else {
				RequestParameters.put(parameterName, null);
			}
		}
		return RequestParameters;
	}

	private String getPDF(
	        Map<String, String> data,
	        String returnValues,
	        String encodedSessionKey) throws Exception {

	    // ================= VALIDATIONS =================

	    if (data == null || data.isEmpty()) {
	        throw new IllegalArgumentException("Data map is null or empty");
	    }

	    String fileType = data.get("FileType");
	    if (fileType == null || !"PDF".equalsIgnoreCase(fileType)) {
	        throw new IllegalArgumentException("Invalid or missing FileType");
	    }

	    if (returnValues == null || returnValues.isEmpty()) {
	        throw new IllegalArgumentException("returnValues is null or empty");
	    }

	    if (encodedSessionKey == null || encodedSessionKey.isEmpty()) {
	        throw new IllegalArgumentException("encodedSessionKey is null or empty");
	    }

	    // ================= GET REFERENCE NUMBER SAFELY =================

	    String referenceNumber = data.get("ReferenceNumber");

	    // emSigner sometimes sends different casing
	    if (referenceNumber == null || referenceNumber.isEmpty()) {
	        referenceNumber = data.get("Referencenumber");
	    }

	    if (referenceNumber == null || referenceNumber.isEmpty()) {
	        referenceNumber = data.get("referenceNumber");
	    }

	    if (referenceNumber == null || referenceNumber.isEmpty()) {
	        throw new IllegalArgumentException("ReferenceNumber is null or empty");
	    }

	    // ================= PDF PROCESSING =================

	    byte[] signedDoc;
	    String base64signedDoc;
	    String saveDir = "docsavepath";

	    try {

	        // Decode values
	        byte[] decodedReturnValues = Base64.getDecoder().decode(returnValues);
	        byte[] decodedSessionKey = Base64.getDecoder().decode(encodedSessionKey);

	        // Decrypt signed document
	        signedDoc = decryptText1(decodedReturnValues, decodedSessionKey);

	        if (signedDoc == null || signedDoc.length == 0) {
	            throw new RuntimeException("Decrypted signed document is null or empty");
	        }

	        base64signedDoc = Base64.getEncoder().encodeToString(signedDoc);

	        String returnStatus = data.get("ReturnStatus");

	        if (!"Success".equalsIgnoreCase(returnStatus) || base64signedDoc.isEmpty()) {
	            throw new RuntimeException("Signing failed or signed document is empty");
	        }

	        // ================= SAVE PDF =================

	        File directory = new File(saveDir);

	        if (!directory.exists()) {
	            directory.mkdirs();
	        }

	        Path filePath = Paths.get(saveDir, referenceNumber + ".pdf");

	        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
	            fos.write(signedDoc);
	        }

	        System.out.println("✅ PDF saved to: " + filePath.toAbsolutePath());

	    } catch (Exception e) {
	        throw new RuntimeException("Failed to process PDF: " + e.getMessage(), e);
	    }

	    return base64signedDoc;
	}

	public byte[] decryptText1(byte[] text, byte[] key) throws Exception {
		byte[] bytePlainText = null;
		try {
			SecretKey dec_Key_original = new SecretKeySpec(key, 0, key.length, "AES");
			Cipher aesCipher = Cipher.getInstance("AES");
			aesCipher.init(Cipher.DECRYPT_MODE, dec_Key_original);
			bytePlainText = aesCipher.doFinal(text);
		} catch (Exception e) {
			System.out.println("Decryption error: " + e.getMessage());
			throw e;
		}
		return bytePlainText;
	}

	 
	private String getFile() throws IOException {
		String filePath = "path";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
		System.out.println("Encoded file \n" + base64Encoded);
		return base64Encoded;
	}
		
	
 
	
	 
	@GetMapping("/downloadpdf")
	public void download(@RequestParam("ref") String referenceNumber, HttpServletResponse response) throws Exception {
	    System.out.println("Download request received for ref: " + referenceNumber);

	    // Retrieve the signed PDF from the session
	    String pdfdata = sessionKeyStore.get("signedPdf_" + referenceNumber);
	    if (pdfdata == null || pdfdata.isEmpty()) {
	        throw new IllegalArgumentException("No signed PDF found for reference number: " + referenceNumber);
	    }

	    byte[] signedPdf = Base64.getDecoder().decode(pdfdata);

	    // Set headers for file download
	    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);
	    response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + referenceNumber + "_signed.pdf");

	    // Write the PDF to the response output stream
	    response.getOutputStream().write(signedPdf);
	    response.getOutputStream().flush();
	}
	
	
}




















 