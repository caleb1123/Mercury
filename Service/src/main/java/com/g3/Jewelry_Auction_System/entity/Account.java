package com.g3.Jewelry_Auction_System.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(unique = true,columnDefinition = "varchar(50)")
    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 50, message = "Username must be 6-50 characters")
    private String userName;

    @Column
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Column
    private String address;

    @Column
    @Past(message = "Date of Birth must be in past")
    private LocalDate dob;

    @Column(unique = true, columnDefinition = "varchar(50)")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Column
    private Boolean sex;

    @Column(unique = true)
    @Pattern(regexp = "^\\d{10,11}$", message = "Phone number must be 10 to 11 digits and contain only numbers")
    @NotBlank(message = "Phone number is required")
    private String phone;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column
    private Boolean status;

    @OneToMany(mappedBy = "account")
    private Collection<Bid> bids;

    @OneToMany(mappedBy = "account")
    private Collection<Payment> payments;

    @OneToMany(mappedBy = "account")
    private Collection<Post> posts;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "account")
    private Collection<Request> requests;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username=" + userName +
                // other fields, but don't include the ones that cause circular references
                '}';
    }
}
