package org.hj.timebean.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "member")
public class Member implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;  // 고정된 serialVersionUID 설정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //공동 정보
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "timer_password")
    private int timerPassword;
    @Column(name = "role")
    private String role; //ROLE_USER, ROLE_ADMIN
    @Column(name = "level")
    private String level; //"씨앗"

    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @Column(name = "profile_url")
    private String profileUrl;

    //읿반 로그인 속성
    private String password;
    private LocalDate last_login;

    // @Pattern(regexp = "@") // 정규표현식

    //oauth 로그인 속성
    @Column(name = "provider")
    private String provider;
    @Column(name = "provider_id")
    private String providerId;

    @PrePersist //엔티티가 영속화되기 전에 호출
    public void prePersist() {
        createdDate = LocalDate.now();
    }

    public Member() {
    }

    public Member(long id, String accountId, String email, String nickname, int timerPassword, String role, String level, LocalDate createdDate, LocalDate updatedDate, byte[] profileImage, String profileUrl, String password, LocalDate last_login, String provider, String providerId) {
        this.id = id;
        this.accountId = accountId;
        this.email = email;
        this.nickname = nickname;
        this.timerPassword = timerPassword;
        this.role = role;
        this.level = level;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.profileImage = profileImage;
        this.profileUrl = profileUrl;
        this.password = password;
        this.last_login = last_login;
        this.provider = provider;
        this.providerId = providerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTimerPassword() {
        return timerPassword;
    }

    public void setTimerPassword(int timerPassword) {
        this.timerPassword = timerPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDate last_login) {
        this.last_login = last_login;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", timerPassword=" + timerPassword +
                ", role='" + role + '\'' +
                ", level='" + level + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", profileImage=" + Arrays.toString(profileImage) +
                ", profileUrl='" + profileUrl + '\'' +
                ", password='" + password + '\'' +
                ", last_login=" + last_login +
                ", provider='" + provider + '\'' +
                ", providerId='" + providerId + '\'' +
                '}';
    }
}
