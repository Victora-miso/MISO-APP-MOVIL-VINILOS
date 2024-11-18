package com.alphazetakapp.misoappvinilos;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
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


public class testCollector {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testListadoCollector() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Collectors")).perform(click());

        ViewInteraction listaCollector = onView(allOf(withId(R.id.CollectorRecyclerView), isDisplayed()));
        listaCollector.check(matches(isDisplayed()));
    }
    @Test
    public void testDetalleCollector() {
        onView(withContentDescription("More options")).perform(click());
        onView(withText("Collectors")).perform(click());

        ViewInteraction listaCollector = onView(allOf(withId(R.id.CollectorRecyclerView), isDisplayed()));
        listaCollector.check(matches(isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listaCollector.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction emailCollector = onView(allOf(withId(R.id.collectorEmailTextView), isDisplayed()));
        emailCollector.check(matches(isDisplayed()));
    }
}
