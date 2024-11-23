package com.example.project1.BLL;

public class AdoptionRequest {

    private String requestID;
    private String userId;
    private String rescueCenterId;
    private String animalId;
    private boolean has_allergy;
    private boolean suitable_living_conditions;
    private String reason_to_adopt;
    boolean applicationStatus;

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRescueCenterId() {
        return rescueCenterId;
    }

    public void setRescueCenterId(String rescueCenterId) {
        this.rescueCenterId = rescueCenterId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public boolean isHas_allergy() {
        return has_allergy;
    }

    public void setHas_allergy(boolean has_allergy) {
        this.has_allergy = has_allergy;
    }

    public boolean isSuitable_living_conditions() {
        return suitable_living_conditions;
    }

    public void setSuitable_living_conditions(boolean suitable_living_conditions) {
        this.suitable_living_conditions = suitable_living_conditions;
    }

    public String getReason_to_adopt() {
        return reason_to_adopt;
    }

    public void setReason_to_adopt(String reason_to_adopt) {
        this.reason_to_adopt = reason_to_adopt;
    }

    public boolean isApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(boolean applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

}
