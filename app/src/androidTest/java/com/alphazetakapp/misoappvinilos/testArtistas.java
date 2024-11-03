package com.alphazetakapp.misoappvinilos;
import  com.alphazetakapp.misoappvinilos.ui.main.MainActivity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.ViewInteraction;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

public class testArtistas {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testListadoArtista() {
        ViewInteraction menuArtista = onView(allOf(withId(R.id.artists), withText("Artists"), isDisplayed()));
        menuArtista.perform(click());

        ViewInteraction listaArtista = onView(allOf(withId(R.id.musiciansRecyclerView), isDisplayed()));
        listaArtista.check(matches(isDisplayed()));
    }

    @Test
    public void testDetalleArtista() {
        ViewInteraction menuArtista = onView(allOf(withId(R.id.artists), withText("Artists"), isDisplayed()));
        menuArtista.perform(click());

        ViewInteraction listaArtista = onView(allOf(withId(R.id.musiciansRecyclerView), isDisplayed()));
        listaArtista.check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listaArtista.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction nombreArtista = onView(allOf(withId(R.id.musicianNameTextView), isDisplayed()));
        nombreArtista.check(matches(isDisplayed()));
    }
}
