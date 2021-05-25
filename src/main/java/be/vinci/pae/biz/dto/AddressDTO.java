package be.vinci.pae.biz.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import be.vinci.pae.biz.impl.AddressImpl;

// TODO: Auto-generated Javadoc
/**
 * The Interface AddressDTO.
 */
@JsonDeserialize(as = AddressImpl.class)

public interface AddressDTO {
  /**
   * Gets the id.
   *
   * @return the id
   */
  int getId();


  /**
   * Sets the id.
   *
   * @param id the new id
   */
  void setId(int id);


  /**
   * Gets the city.
   *
   * @return the city
   */
  String getCity();

  /**
   * Gets the country.
   *
   * @return the country
   */
  String getCountry();

  /**
   * Sets the country.
   *
   * @param country the new country
   */
  void setCountry(String country);

  /**
   * Sets the city.
   *
   * @param city the new city
   */
  void setCity(String city);


  /**
   * Gets the building number.
   *
   * @return the building number
   */
  String getBuildingNumber();


  /**
   * Sets the building number.
   *
   * @param buildingNumber the new building number
   */
  void setBuildingNumber(String buildingNumber);


  /**
   * Gets the street.
   *
   * @return the street
   */
  String getStreet();


  /**
   * Sets the street.
   *
   * @param street the new street
   */
  void setStreet(String street);


  /**
   * Gets the post code.
   *
   * @return the post code
   */
  int getPostCode();


  /**
   * Sets the post code.
   *
   * @param postCode the new post code
   */
  void setPostCode(int postCode);
}
