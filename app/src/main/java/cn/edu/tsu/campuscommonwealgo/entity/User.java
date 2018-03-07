package cn.edu.tsu.campuscommonwealgo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by XWM on 2018/1/17.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    @Unique
    private String username;
    @NotNull
    private String password;
    private String nickname;
    private String avatarUrl;
    private String school;
    private String token;
    @NotNull
    private boolean thirdPartyUser;
    @Generated(hash = 527033921)
    public User(Long id, @NotNull String username, @NotNull String password,
            String nickname, String avatarUrl, String school, String token,
            boolean thirdPartyUser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.school = school;
        this.token = token;
        this.thirdPartyUser = thirdPartyUser;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAvatarUrl() {
        return this.avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public String getSchool() {
        return this.school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public boolean getThirdPartyUser() {
        return this.thirdPartyUser;
    }
    public void setThirdPartyUser(boolean thirdPartyUser) {
        this.thirdPartyUser = thirdPartyUser;
    }
}
