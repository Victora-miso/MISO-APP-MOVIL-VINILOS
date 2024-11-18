package com.alphazetakapp.misoappvinilos;
import  com.alphazetakapp.misoappvinilos.ui.main.MainActivity;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.ViewInteraction;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;



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
}
