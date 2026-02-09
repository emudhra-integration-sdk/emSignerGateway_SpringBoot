package com.example.utils;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {

    private String Name = "Test";
    private String FileType = "PDF";
    private String Data = null;
    private int SignatureType = 0;
    private String SelectPage = "LAST";
    private String SignaturePosition = "Bottom-Right";
    private String AuthToken = " ";
    private String File = "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c2VGb250IC9UaW1lcy1Cb2xkCi9TdWJ0eXBlIC9UeXBlMQovRW5jb2RpbmcgL1dpbkFuc2lFbmNvZGluZwovRmlyc3RDaGFyIDMyCi9MYXN0Q2hhciAyNTUKPj4KZW5kb2JqCjIgMCBvYmoKPDwKL1Byb2NTZXQgWy9QREYgL1RleHQgL0ltYWdlQiAvSW1hZ2VDIC9JbWFnZUldCi9Gb250IDw8Ci9GOSA3IDAgUgovRjEwIDggMCBSCj4+Ci9YT2JqZWN0IDw8Cj4+Cj4+CmVuZG9iago5IDAgb2JqCjw8Ci9Qcm9kdWNlciAoanNQREYgMi41LjIpCi9DcmVhdGlvbkRhdGUgKEQ6MjAyNTAzMDMyMTUxNDcrMDUnMzAnKQo+PgplbmRvYmoKMTAgMCBvYmoKPDwKL1R5cGUgL0NhdGFsb2cKL1BhZ2VzIDEgMCBSCi9PcGVuQWN0aW9uIFszIDAgUiAvRml0SCBudWxsXQovUGFnZUxheW91dCAvT25lQ29sdW1uCj4+CmVuZG9iagp4cmVmCjAgMTEKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDA0Mjg0IDAwMDAwIG4gCjAwMDAwMDQ2MDAgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAwMTUyIDAwMDAwIG4gCjAwMDAwMDI3MjYgMDAwMDAgbiAKMDAwMDAwMjg2MyAwMDAwMCBuIAowMDAwMDA0MzQ3IDAwMDAwIG4gCjAwMDAwMDQ0NzQgMDAwMDAgbiAKMDAwMDAwNDcxNSAwMDAwMCBuIAowMDAwMDA0ODAwIDAwMDAwIG4gCnRyYWlsZXIKPDwKL1NpemUgMTEKL1Jvb3QgMTAgMCBSCi9JbmZvIDkgMCBSCi9JRCBbIDxEMTQxQzZERjk1QTgyN0RBNEY1RUUyMTlCRDExRUZCNT4gPEQxNDFDNkRGOTVBODI3REE0RjVFRTIxOUJEMTFFRkI1PiBdCj4+CnN0YXJ0eHJlZgo0OTA0CiUlRU9G";
    private String filepath = null;
    private String PageNumber = null;
    private int Noofpages = 0;
    private boolean PreviewRequired = true;
    private String PagelevelCoordinates = null;
    private String CustomizeCoordinates = null;
    private String AadhaarNumber = null;
    private String SUrl = "https://gannet-excited-mako.ngrok-free.app/api/success";
    private String FUrl = "https://gannet-excited-mako.ngrok-free.app/api/canceled";
    private String CUrl = "https://gannet-excited-mako.ngrok-free.app/api/canceled";
    public String ReferenceNumber = UUID.randomUUID().toString().replace("-", "");
    private boolean Enableuploadsignature = true;
    private boolean Enablefontsignature = true;
    private boolean EnableDrawSignature = true;
    private boolean EnableeSignaturePad = false;
    private boolean IsCompressed = false;
    private boolean IsCosign = true;
    private int eSignAuthmode = 0;
    private boolean EnableViewDocumentLink = false;
    private boolean Storetodb = false;
    private boolean IsGSTN = false;
    private boolean IsGSTN3B = false;
    private String SignatureImage = null;
    private boolean IsCustomized = false;
    private String eSign_SignerId = null;
    private String Documentdetails = null;
    private String FileData = null;
    private String Doctype = null;
    private String PostbackUrl = null;
    private String Objky = null;
    private String TransactionNumber = null;
    private String FinancialYear = null;
    private String EmployeeId = null;
    private String SignatureMode = "12";
    private int AuthenticationMode = 1;
    private boolean EnableInitials = false;
    private boolean IsInitialsCustomized = false;
    private String SelectInitialsPage = null;
    private String InitialsPosition = null;
    private String InitialsCustomizeCoordinates = null;
    private String InitialsPagelevelCordinates = null;
    private String InitialsPageNumbers = null;
    private boolean ValidateAllPlaceholders = false;
    private String Searchtext = "Signature of the claimant";
    private String Anchor = "Top";
    private String InitialSearchtext = null;
    private String InitialsAnchor = null;
    private boolean HDFCflag = false;
    private String Reason = null;
    private String eSignGatewayCustomUI = "";
    private boolean EnableFetchSignerInfo = true;
    //private String DynamicContent = "W3siS2V5IjoiZVNpZ25lZCBieSIsIlZhbHVlIjoiU2FudGhvc2ggS3VtYXIgUGVydW1hbHNhbXkifSx7IktleSI6Ik9uIGJlaGFsZiBvZiIsIlZhbHVlIjoiRW11ZGhyYSB0ZXN0aW5nIGZvciBhZ3JlZW1lbnQifSx7IktleSI6IlB1cnBvc2UiLCJWYWx1ZSI6IkFncmVlbWVudCBTaWduYXR1cmUifSx7IktleSI6IkxvY2F0aW9uIiwiVmFsdWUiOiIxc3QgU2VjdG9yLCBIU1IgTGF5b3V0LCBCZW5nYWx1cnUsIEthcm5hdGFrYSwgSW5kaWEifSx7IktleSI6bnVsbCwiVmFsdWUiOiJlU2lnbmVkIHVzaW5nIEFhZGhhYXIgKHZlcmk1ZGlnaXRhbC5jb20pIn0seyJLZXkiOiJEYXRlIiwiVmFsdWUiOiJEYXRlVGltZSJ9XQ==";

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setFile(String file) {
        this.File=file;
    }

    private void setPagelevelCoordinates(){

    }

    public String coordinateByFormType(String formType){
        if (formType.equals("FORM D")){
            return "3,453,660,573,600;3,448,583,568,523";
        }

        return null;
    }
}