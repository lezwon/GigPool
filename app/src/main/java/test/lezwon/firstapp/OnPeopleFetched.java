package test.lezwon.firstapp;

import com.google.api.services.people.v1.model.Person;

/**
 * Created by Lezwon on 23-11-2016.
 */
public interface OnPeopleFetched {
    void onPeopleFetched(Person person);
}
