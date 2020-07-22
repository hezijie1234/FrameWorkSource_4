package com.zijie.xiangxuewebview.command;


import com.zijie.xiangxuewebview.utils.WebConstants;

public class AccountLevelCommands extends Commands {

    public AccountLevelCommands() {
    }

    @Override
    int getCommandLevel() {
        return WebConstants.LEVEL_ACCOUNT;
    }

}
