package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.NotNull;

public class ListedActivityInputDto {

    @NotNull
    private Long employeeId;

    @NotNull
    private ActivityInputDto activity;

    @NotNull
    private  boolean validated;

    protected ListedActivityInputDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public Long getEmployeeId() { return employeeId; }
    public ActivityInputDto getActivity() { return activity; }
    public boolean isValidated() { return validated; }
}
