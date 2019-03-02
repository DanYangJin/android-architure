package com.shouzhan.design;

import com.shouzhan.design.repository.LoginRepository;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    LoginRepository repository = new LoginRepository();

    @Test
    public void login() {
        repository.toLogin("danbin", "111111");
    }

}