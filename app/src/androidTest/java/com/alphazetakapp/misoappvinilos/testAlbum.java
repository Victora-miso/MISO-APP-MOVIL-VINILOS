package com.alphazetakapp.misoappvinilos;
import  com.alphazetakapp.misoappvinilos.ui.main.MainActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.ViewInteraction;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import android.widget.DatePicker;


public class testAlbum {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testListadoAlbum() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Albums")).perform(click());

        ViewInteraction listaAlbum = onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()));
        listaAlbum.check(matches(isDisplayed()));
    }
    @Test
    public void testDetalleAlbum() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Albums")).perform(click());

        ViewInteraction listaAlbum = onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()));
        listaAlbum.check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listaAlbum.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction musicianName = onView(allOf(withId(R.id.musicianNameTextView), isDisplayed()));
        musicianName.check(matches(isDisplayed()));
    }
    @Test
    public void testCrearAlbum() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("New Album")).perform(click());
        onView(withId(R.id.inputTitleText)).perform(replaceText("Happier than ever"));
        onView(withId(R.id.inputCoverText)).perform(replaceText("https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/2d/f3/c9/2df3c9fd-e0eb-257c-c035-b04f05a66580/21UMGIM36691.rgb.jpg/1200x1200bb.jpg"));
        onView(withId(R.id.inputDateText)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(setDate(2021, 7, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.inputDescriptionText)).perform(replaceText("Happier than ever es el segundo álbum de Billie Eilish"));
        onView(withId(R.id.inputGenreSpinner)).perform(click());
        onView(withText("Rock")).perform(click());
        onView(withId(R.id.inputLabelSpinner)).perform(click());
        onView(withText("Sony Music")).perform(click());
        onView(withId(R.id.createAlbumButton)).perform(click());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("Happier than ever")).check(matches(isDisplayed()));


    }
}
