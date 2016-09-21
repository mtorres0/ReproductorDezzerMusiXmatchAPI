package com.telstock.tmanager.proyectocursoandroid;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by usr_micro9 on 3/08/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    public static final String STRING_TO_BE_TYPED = "creep";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void searchTestActivity(){
        onView(withId(R.id.action_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(STRING_TO_BE_TYPED), pressKey(KeyEvent.KEYCODE_SEARCH));

        //Verifica que el texto cambio.
        onView(isAssignableFrom(EditText.class)).check(matches(withText(STRING_TO_BE_TYPED)));
    }
//
//    @Test
//    public void listTest(){
////        onView(withId(R.id.action_search)).perform(click());
////        onView(isAssignableFrom(EditText.class)).perform(typeText(STRING_TO_BE_TYPED), pressKey(KeyEvent.KEYCODE_SEARCH));
//
//        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
//        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
//        //Here's the difference
//        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_cancion));
//
////        onView(withId(R.id.rv_lista)).perform(RecyclerViewActions.scrollToPosition(3));
//        onView(withId(R.id.rv_lista)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
//    }

    @Test
    public void listItemTest(){

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //Here's the difference
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_cancion));

        onView (withId (R.id.rv_lista)).check(ViewAssertions.matches (ViewMatchers.withListSize (2)));

    }


    @Test
    public void checkTitle(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //Here's the difference
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_album));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(R.string.albumes_favoritos)));
    }


}
