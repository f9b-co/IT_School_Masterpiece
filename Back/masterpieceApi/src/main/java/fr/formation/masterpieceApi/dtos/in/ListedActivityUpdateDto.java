package fr.formation.masterpieceApi.dtos.in;

import javax.validation.constraints.NotNull;

public class ListedActivityUpdateDto {

    @NotNull
    private Long employeeId;

    @NotNull
    private ActivityInputDto activity;

    @NotNull
    private  boolean validated;

    protected ListedActivityUpdateDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public Long getEmployeeId() { return employeeId; }
    public ActivityInputDto getActivity() { return activity; }
    public boolean isValidated() { return validated; }
}
