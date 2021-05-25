package be.vinci.pae.biz.impl;

import be.vinci.pae.biz.feature.Address;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressImpl.
 */
public class AddressImpl implements Address {

  /** The id. */
  int id;

  /** The building number. */
  String buildingNumber;

  /** The city. */
  String city;

  /** The country. */
  String country;
  /** The post code. */
  int postCode;

  /** The street. */
  String street;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the building number.
   *
   * @return the building number
   */
  public String getBuildingNumber() {
    return buildingNumber;
  }

  /**
   * Sets the building number.
   *
   * @param buildingNumber the new building number
   */
  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  /**
   * Gets the city.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city.
   *
   * @param city the new city
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Gets the post code.
   *
   * @return the post code
   */
  public int getPostCode() {
    return postCode;
  }

  /**
   * Sets the post code.
   *
   * @param postCode the new post code
   */
  public void setPostCode(int postCode) {
    this.postCode = postCode;
  }

  /**
   * Gets the street.
   *
   * @return the street
   */
  @Override
  public String getStreet() {
    return street;
  }

  /**
   * Sets the street.
   *
   * @param street the new street
   */
  @Override
  public void setStreet(String street) {
    this.street = street;
  }

  /**
   * Gets the country.
   *
   * @return the country
   */
  @Override
  public String getCountry() {
    return country;
  }

  /**
   * Sets the country.
   */
  @Override
  public void setCountry(String country) {
    this.country = country;

  }
}
