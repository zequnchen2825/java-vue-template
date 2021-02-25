package com.woc.common.vo;

import com.woc.common.entity.WocChannel;
import com.woc.common.entity.WocPlatform;
import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocUser;
import com.woc.common.entity.extend.WocMenuExtend;

import java.util.List;

public class LoginVO {

    private WocUser user;

    private List<WocRole> roles;

    private List<String> permissions;

    private List<WocMenuExtend> menus;

    private WocPlatform platform;

    private List<WocChannel> channels;

    public WocUser getUser() {
        return user;
    }

    public void setUser(WocUser user) {
        this.user = user;
    }

    public List<WocRole> getRoles() {
        return roles;
    }

    public void setRoles(List<WocRole> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<WocMenuExtend> getMenus() {
        return menus;
    }

    public void setMenus(List<WocMenuExtend> menus) {
        this.menus = menus;
    }

    public WocPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(WocPlatform platform) {
        this.platform = platform;
    }

    public List<WocChannel> getChannels() {
        return channels;
    }

    public void setChannels(List<WocChannel> channels) {
        this.channels = channels;
    }

}
