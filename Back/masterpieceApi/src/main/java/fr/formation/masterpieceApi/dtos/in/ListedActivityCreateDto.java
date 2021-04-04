package fr.formation.masterpieceApi.dtos.in;

import validations.NoSimultaneousListedActivityForOneEmployee;

import javax.validation.constraints.NotNull;

@NoSimultaneousListedActivityForOneEmployee
public class ListedActivityCreateDto {

    @NotNull
    private Long employeeId;

    @NotNull
    private ActivityInputDto activity;

    @NotNull
    private  boolean validated;

    protected ListedActivityCreateDto() {
        // Empty no-arg constructor (Hibernate)
    }

    public Long getEmployeeId() { return employeeId; }
    public ActivityInputDto getActivity() { return activity; }
    public boolean isValidated() { return validated; }
}
