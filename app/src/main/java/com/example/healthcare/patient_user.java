package com.example.healthcare;

public class patient_user  {
    private String patientName;
    private String patientAge;
    private String patientAddress;
    private String patientDisease;
    private String patientPhone;
    private String fromDate;
    private String toDate;
    private String fromTime;
    private String toTime;

    private String pProfilePicture;
    private String reportCardPicture;




    public patient_user() {
    }


    public patient_user(String patientName, String patientAge, String patientAddress, String patientDisease,
                        String patientPhone, String fromDate, String toDate, String fromTime, String toTime, String pProfilePicture,String reportCardPicture) {
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientAddress = patientAddress;
        this.patientDisease = patientDisease;
        this.patientPhone = patientPhone;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.reportCardPicture = reportCardPicture;
        this.pProfilePicture = pProfilePicture;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientDisease() {
        return patientDisease;
    }

    public void setPatientDisease(String patientDisease) {
        this.patientDisease = patientDisease;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getpProfilePicture(){
        return pProfilePicture;
    }
    public void setpProfilePicture(String profilePicture){
        this.pProfilePicture = pProfilePicture;
    }

    public String getReportCardPicture() {
        return reportCardPicture;
    }



    public void setReportCardPicture(String reportCardPicture) {
        this.reportCardPicture = reportCardPicture;
    }
}
