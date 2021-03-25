package fr.formation.masterpieceApi.dtos.tmpDto;

public class ListedActivitiesForOneDto {

    private ActivityViewDto activity;
    private  boolean validated;

    public ListedActivitiesForOneDto(ActivityViewDto activity, boolean validated) {
        this.activity = activity;
        this.validated = validated;
    }

    public ActivityViewDto getActivity() { return activity; }
    public boolean isValidated() { return validated; }

}
