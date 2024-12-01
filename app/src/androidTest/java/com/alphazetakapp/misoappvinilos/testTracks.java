package com.alphazetakapp.misoappvinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.alphazetakapp.misoappvinilos.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;


public class testTracks {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testTrack() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Associate Track")).perform(click());
        onView(withId(R.id.inputTrackNameText)).perform(typeText("Track 1"));
        onView(withId(R.id.inputDurationText)).perform(typeText("01:30"));
        onView(withId(R.id.createTrackButton)).perform(click());

        ViewInteraction listaAlbum = onView(allOf(withId(R.id.albumsRecyclerView), isDisplayed()));
        listaAlbum.check(matches(isDisplayed()));

    }
}