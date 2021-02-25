package com.woc.common.entity.extend;



import com.woc.common.entity.WocMenu;

import java.util.List;

/**
 * woc_menu
 * @author 
 */
public class WocMenuExtend extends WocMenu {

    private List<WocMenuExtend> subWocMenu;

    public List<WocMenuExtend> getSubWocMenu() {
        return subWocMenu;
    }

    public void setSubWocMenu(List<WocMenuExtend> subWocMenu) {
        this.subWocMenu = subWocMenu;
    }
}