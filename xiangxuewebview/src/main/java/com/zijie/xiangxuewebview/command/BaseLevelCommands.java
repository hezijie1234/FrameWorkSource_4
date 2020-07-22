package com.zijie.xiangxuewebview.command;


import com.zijie.xiangxuewebview.utils.WebConstants;

public class BaseLevelCommands extends Commands {

    public BaseLevelCommands() {
    }

    @Override
    int getCommandLevel() {
        return WebConstants.LEVEL_BASE;
    }
}
