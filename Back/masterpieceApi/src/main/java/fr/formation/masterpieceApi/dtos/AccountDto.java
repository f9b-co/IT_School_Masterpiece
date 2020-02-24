package fr.formation.masterpieceApi.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountDto {

    private final String passPattern = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$|" +
            "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\W).*$|" +
            "^(?=.*?[a-z])(?=.*?[0-9])(?=.*?\\W).*$|" +
            "^(?=.*?[A-Z])(?=.*?[0-9])(?=.*?\\W).*$";

    @NotNull
    private  boolean enable;
    @NotBlank
    @Size(min = 7, max = 7)
    private String accountUsername;
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = passPattern)
    private String password;

    public AccountDto() {
        //
    }

    public boolean isEnable() {return enable;}
    public String getAccountUsername() { return accountUsername; }
    public String getPassword() { return password; }
}