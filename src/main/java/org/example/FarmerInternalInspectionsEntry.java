package org.example;

public class FarmerInternalInspectionsEntry{
    private String inspectedBy;
    private String mobileNo;
    private String inspectionFromDate;
    private String inspectionToDate;

    private String farmerRegistrationNo;

    public String getInspectedBy() {
        return inspectedBy;
    }

    public void setInspectedBy(String inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getInspectionFromDate() {
        return inspectionFromDate;
    }

    public void setInspectionFromDate(String inspectionFromDate) {
        this.inspectionFromDate = inspectionFromDate;
    }

    public String getInspectionToDate() {
        return inspectionToDate;
    }

    public void setInspectionToDate(String inspectionToDate) {
        this.inspectionToDate = inspectionToDate;
    }

    public String getFarmerRegistrationNo() {
        return farmerRegistrationNo;
    }

    public void setFarmerRegistrationNo(String farmerRegistrationNo) {
        this.farmerRegistrationNo = farmerRegistrationNo;
    }
}