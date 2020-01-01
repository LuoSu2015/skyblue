package com.cskaoyan.shiro.config;

import com.cskaoyan.shiro.token.UserToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class CustomAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();
        Collection<Realm> originRealms = this.getRealms();
        UserToken user = (UserToken) authenticationToken;
        String type = user.getType();
        ArrayList<Realm> realms = new ArrayList<>();
        for (Realm realm : originRealms) {
            if (realm.getName().toLowerCase().contains(type)) {
                realms.add(realm);
            }
        }
        return realms.size() == 1 ? this.doSingleRealmAuthentication((Realm) realms.iterator().next(), authenticationToken) : this.doMultiRealmAuthentication(realms, authenticationToken);
    }
}
