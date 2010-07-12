/*
 ===========================================================================
 @    $Author$
 @  $Revision$
 @      $Date$
 @
 ===========================================================================
 */
package org.apache.shindig.social.sample.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shindig.auth.SecurityToken;
import org.apache.shindig.social.core.model.ActivityImpl;
import org.apache.shindig.social.core.model.AddressImpl;
import org.apache.shindig.social.core.model.NameImpl;
import org.apache.shindig.social.core.model.PersonImpl;
import org.apache.shindig.social.opensocial.model.Activity;
import org.apache.shindig.social.opensocial.model.Address;
import org.apache.shindig.social.opensocial.model.Name;
import org.apache.shindig.social.opensocial.model.Person;
import org.apache.shindig.social.opensocial.model.Person.Gender;
import org.apache.shindig.social.opensocial.spi.GroupId;
import org.apache.shindig.social.opensocial.spi.UserId;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Adapter class to connect to the persistence of the SNS. <br>
 * For the moment there is no real connection implemented. The data is "stored" in maps.
 */
public class ShindigIntegratorPersistenceAdapter {

  private Map<String, Person> persons;

  private Map<String, List<Person>> friends;

  private Map<String, Map<String, String>> personAppData;

  private Map<String, Activity> activities;

  private static int nextId = 1;

  private int getNextId() {
    return nextId++;
  }

  public ShindigIntegratorPersistenceAdapter() {
    init();
  }

  private static ShindigIntegratorPersistenceAdapter instance;

  public static ShindigIntegratorPersistenceAdapter getInstance() {
    if (instance == null) {
      instance = new ShindigIntegratorPersistenceAdapter();
    }

    return instance;
  }

  private void init() {
    if (persons == null) {
      // create personlist
      persons = new HashMap<String, Person>();

      Name name = new NameImpl();
      name.setGivenName("James");
      name.setFamilyName("Bond");
      name.setFormatted("James Bond");
      Person john = new PersonImpl("john.doe", "007", name);
      Address address = new AddressImpl();
      address.setStreetAddress("Kaiser-Josef-Strasse");
      address.setPostalCode("75105");
      address.setLocality("Freiburg");
      address.setCountry("Germany");
      john.setAddresses(Lists.newArrayList(address));
      john.setGender(Gender.male);
      john.setHasApp(true);
      john.setLanguagesSpoken(Lists.newArrayList("German", "English", "Spanish"));
      persons.put(john.getId(), john);

      name = new NameImpl();
      name.setGivenName("Anya");
      name.setFamilyName("Amasova");
      name.setFormatted("Anya Amasova");
      Person jane = new PersonImpl("jane.doe", "Anya", name);
      jane.setGender(Gender.female);
      jane.setHasApp(true);
      persons.put(jane.getId(), jane);

      name = new NameImpl();
      name.setGivenName("Felix");
      name.setFamilyName("Leiter");
      name.setFormatted("Felix Leiter");
      Person george = new PersonImpl("george.doe", "Felix", name);
      george.setGender(Gender.male);
      george.setHasApp(true);
      persons.put(george.getId(), george);

      name = new NameImpl();
      name.setGivenName("Jack");
      name.setFamilyName("Wade");
      name.setFormatted("Jack Wade");
      Person mario = new PersonImpl("mario.rossi", "Jack", name);
      mario.setGender(Gender.male);
      mario.setHasApp(true);
      persons.put(mario.getId(), mario);

      name = new NameImpl();
      name.setGivenName("Holly");
      name.setFamilyName("Goodhead");
      name.setFormatted("Holly Goodhead");
      Person maija = new PersonImpl("maija.m", "Holly", name);
      maija.setGender(Gender.female);
      maija.setHasApp(true);
      persons.put(maija.getId(), maija);

      // create friendlist. The key of a Map entry is the person id.
      friends = new HashMap<String, List<Person>>();
      List<Person> friendList = new ArrayList<Person>();
      friendList.add(jane);
      friendList.add(george);
      friendList.add(maija);
      friendList.add(mario);
      friends.put("john.doe", friendList);

      friendList = new ArrayList<Person>();
      friendList.add(john);
      friendList.add(mario);
      friends.put("jane.doe", friendList);

      friendList = new ArrayList<Person>();
      friendList.add(john);
      friends.put("george.doe", friendList);

      friendList = new ArrayList<Person>();
      friends.put("maija.m", friendList);

      // create appData. The key of a Map entry is the person id.
      personAppData = Maps.newHashMap();
      Map<String, String> data = Maps.newHashMap();
      data.put("APP_1.HIGHSCORE", "7534");
      data.put("APP_1.RANG", "8");
      data.put("APP_2.KEY1", "Applicationdata from app 2");
      personAppData.put("john.doe", data);

      data = Maps.newHashMap();
      data.put("APP_1.HIGHSCORE", "10000");
      data.put("APP_1.RANG", "1");
      personAppData.put("maija.m", data);

      // create activitylist
      activities = new HashMap<String, Activity>();

      Activity activity = new ActivityImpl("" + getNextId(), "john.doe");
      activity.setAppId("App1");
      activity.setTitle("First Activity");
      activity.setBody("Body of first Activity.");
      activities.put(activity.getId(), activity);

      activity = new ActivityImpl("" + getNextId(), "john.doe");
      activity.setAppId("App1");
      activity.setTitle("Second Activity");
      activity.setBody("Body of second Activity.");
      activities.put(activity.getId(), activity);

      activity = new ActivityImpl("" + getNextId(), "john.doe");
      activity.setAppId("Container");
      activity.setTitle("Joined Group XY");
      activities.put(activity.getId(), activity);

      activity = new ActivityImpl("" + getNextId(), "george.doe");
      activity.setAppId("App1");
      activity.setTitle("Felix' first Activity");
      activity.setBody("Body of the Activity.");
      activities.put(activity.getId(), activity);
    }
  }

  public Person findPerson(String id) {
    Person person = persons.get(id);
    return person;
  }

  /**
   * Retrieves the friends of a given person
   * 
   * @param id
   *          person, whose friends should be listed
   * @return List of Persons.
   */
  public List<Person> getFriendList(String id) {
    List<Person> friendList = friends.get(id);
    if (friendList == null) {
      friendList = new ArrayList<Person>();
    }
    return friendList;
  }

  /**
   * Retrieves the applicationdata of a given person
   * 
   * @param id
   *          person, whose appdata should be returned
   * @return Map with appdata
   */
  public Map<String, String> getAppData(String id) {
    Map<String, String> appData = personAppData.get(id);
    if (appData == null) {
      appData = Maps.newHashMap();
    }
    return appData;
  }

  /**
   * Gets a list of Persons who stay in relation to the passed in user.
   * 
   * @param user
   * @param group
   * @param token
   * @return list of persons.
   */
  public List<Person> getRelatedPersons(UserId user, GroupId group, SecurityToken token) {
    List<Person> result = new ArrayList<Person>();
    String userId = user.getUserId(token);

    if (group == null) {
      result.add(findPerson(userId));
    }
    else {
      switch (group.getType()) {
        case all:
        case friends:
        case groupId:
          List<Person> friendList = getFriendList(userId);
          if (friendList != null) {
            result.addAll(friendList);
          }
          break;
        case self:
          result.add(findPerson(userId));
          break;
      }
    }

    return result;
  }

  /**
   * Gets a list of Persons who stay in relation to the passed in user List.
   * 
   * @param userIds
   * @param group
   * @param token
   * @return list of persons.
   */
  public List<Person> getRelatedPersons(Set<UserId> userIds, GroupId group, SecurityToken token) {
    List<Person> result = new ArrayList<Person>();

    for (UserId user : userIds) {
      List<Person> people = getRelatedPersons(user, group, token);

      // Add only those people who are not already added.
      Iterator<Person> iteratorPeople = people.iterator();
      while (iteratorPeople.hasNext()) {
        Person currentPerson = iteratorPeople.next();
        if (!containsPerson(result, currentPerson.getId())) {
          result.add(currentPerson);
        }
      }
    }

    return result;
  }

  /**
   * Checks, if the given person is in the personList
   * 
   * @param personList
   * @param id
   * @return true, if the list contains the person.
   */
  private boolean containsPerson(List<Person> personList, String id) {
    Iterator<Person> iter = personList.iterator();
    while (iter.hasNext()) {
      Person currentPerson = iter.next();
      if (currentPerson.getId().equals(id)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Adds a new activity
   * 
   * @param activity
   *          the new activity to add
   */
  public void addNewActivity(Activity activity) {
    if (activity.getId() == null || activity.getId().equals("")) {
      activity.setId("" + getNextId());
    }
    activities.put(activity.getId(), activity);
  }

  public Activity findActivity(String activityId) {
    Activity activity = activities.get(activityId);
    return activity;
  }

  public List<Activity> getActivities(String userId) {
    List<Activity> result = new ArrayList<Activity>();

    Set<String> keys = activities.keySet();
    Iterator<String> iter = keys.iterator();
    while (iter.hasNext()) {
      String key = iter.next();
      Activity currentActivity = activities.get(key);
      if (currentActivity.getUserId().equals(userId)) {
        result.add(currentActivity);
      }
    }

    return result;
  }
}
