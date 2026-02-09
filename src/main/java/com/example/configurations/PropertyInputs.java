package com.example.configurations;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component

public class PropertyInputs {

    @Value("${CERT_FILE}")
    private String CERT_FILE;

    @Value("${TEMP_FILE}")
    private String TEMP_FILE;

    @Value("${PDF}")
    private String PDF;

    @Value("${PFX}")
    private String PFX;

    @Value("${LOG_FOLDER}")
    private String LOG_FOLDER;

    @Value("${RESPONSE_URL}")
    private String RESPONSE_URL;

    @Getter
    private static PropertyInputs propertyInputs;

    @PostConstruct
    public void init() {
        propertyInputs = this;
    }

    public static PropertyInputs getPropertyInputs() {
        return propertyInputs;
    }

	public String getCERT_FILE() {
		return CERT_FILE;
	}

	public void setCERT_FILE(String cERT_FILE) {
		CERT_FILE = cERT_FILE;
	}

	public String getTEMP_FILE() {
		return TEMP_FILE;
	}

	public void setTEMP_FILE(String tEMP_FILE) {
		TEMP_FILE = tEMP_FILE;
	}

	public String getPDF() {
		return PDF;
	}

	public void setPDF(String pDF) {
		PDF = pDF;
	}

	public String getPFX() {
		return PFX;
	}

	public void setPFX(String pFX) {
		PFX = pFX;
	}

	public String getLOG_FOLDER() {
		return LOG_FOLDER;
	}

	public void setLOG_FOLDER(String lOG_FOLDER) {
		LOG_FOLDER = lOG_FOLDER;
	}

	public String getRESPONSE_URL() {
		return RESPONSE_URL;
	}

	public void setRESPONSE_URL(String rESPONSE_URL) {
		RESPONSE_URL = rESPONSE_URL;
	}

	public static void setPropertyInputs(PropertyInputs propertyInputs) {
		PropertyInputs.propertyInputs = propertyInputs;
	}
    
    
}