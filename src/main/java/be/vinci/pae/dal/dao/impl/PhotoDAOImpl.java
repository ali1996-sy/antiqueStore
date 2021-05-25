package be.vinci.pae.dal.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.dal.DalBackendServices;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.dal.dao.PhotoDAO;
import be.vinci.pae.exception.FatalException;
import jakarta.inject.Inject;


// TODO: Auto-generated Javadoc
/**
 * The Class PhotoDAOImpl.
 */

public class PhotoDAOImpl implements PhotoDAO {
  /** The option factory. */
  @Inject
  private PhotoFactory photoFactory;



  /** The dal service. */
  @Inject
  private DalBackendServices dalService;

  /** The furniture dao. */
  @Inject
  private FurnitureDAO furnitureDao;

  /**
   * Select all photo by furniture id.
   *
   * @param furnitureId the furniture id
   * @return the sets the
   */

  @Override
  public Set<PhotoDTO> selectAllPhotoByFurnitureId(int furnitureId) {
    PreparedStatement selectAllPhotoByFurnitureId =
        dalService.getPreparedStatement("SELECT * FROM project.photos p WHERE p.furniture = ?");
    try {
      selectAllPhotoByFurnitureId.setInt(1, furnitureId);
      try (ResultSet rs = selectAllPhotoByFurnitureId.executeQuery()) {
        Set<PhotoDTO> setPhotoDTOs = new HashSet<PhotoDTO>();
        while (rs.next()) {
          setPhotoDTOs.add(setPhotoDto(rs));
        }

        return setPhotoDTOs;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }


  /**
   * Select all photo.
   *
   * @return the sets the
   */
  @Override
  public Set<PhotoDTO> selectAllPhoto() {
    PreparedStatement selectAllPhoto = dalService
        .getPreparedStatement("SELECT *,f.state FROM project.photos p ,project.furniture f "
            + "WHERE p.photo_id =f.favorite_photo");
    try {
      try (ResultSet rs = selectAllPhoto.executeQuery()) {
        Set<PhotoDTO> setPhotoDTOs = new HashSet<PhotoDTO>();
        while (rs.next()) {
          PhotoDTO photo = setPhotoDto(rs);
          photo.setFurnitureState(rs.getString("state"));
          setPhotoDTOs.add(photo);
        }
        return setPhotoDTOs;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage().toString());
    }
  }

  /**
   * Select photos by type.
   *
   * @param type the type
   * @return the sets the
   */
  @Override
  public Set<PhotoDTO> selectPhotosByType(String type) {
    PreparedStatement selectAllPhoto = dalService.getPreparedStatement(
        "SELECT * ,f.state FROM project.photos p ,project.furniture f,project.furniture_types t "
            + "WHERE p.photo_id =f.favorite_photo AND "
            + "f.furniture_type=t.furniture_type_id AND t.name=?");
    try {
      selectAllPhoto.setString(1, type);
      try (ResultSet rs = selectAllPhoto.executeQuery()) {
        Set<PhotoDTO> setPhotoDTOs = new HashSet<PhotoDTO>();
        while (rs.next()) {
          PhotoDTO photo = setPhotoDto(rs);
          photo.setFurnitureState(rs.getString("state"));
          setPhotoDTOs.add(photo);
        }
        return setPhotoDTOs;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage().toString());
    }
  }

  /**
   * Adds the photos.
   *
   * @param furniture the furniture
   * @param photo the photo
   * @return the photo DTO
   */
  @Override
  public PhotoDTO addPhotos(int furniture, String photo) {
    PreparedStatement insertPhoto = dalService.getPreparedStatement("INSERT INTO project.photos("
        + "     photo, furniture)" + "    VALUES ( ?, ?) returning photo_id");
    try {
      insertPhoto.setString(1, photo);
      insertPhoto.setInt(2, furniture);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage().toString());
    }
    try (ResultSet rs = insertPhoto.executeQuery()) {
      if (rs.next()) {
        PhotoDTO photoDto = photoFactory.getPhotoDTO();
        photoDto.setPhotoId(rs.getInt("photo_id"));
        return photoDto;
      } else {
        throw new FatalException("Photo not found!");
      }
    } catch (Exception e) {
      throw new FatalException(e.getMessage().toString());
    }
  }


  /**
   * Select all photo by visit request id.
   *
   * @param visitRequestId the visit request id
   * @return the map
   */
  @Override
  public Map<PhotoDTO, FurnitureDTO> selectAllPhotoByVisitRequestId(int visitRequestId) {
    PreparedStatement selectAllPhotoByFurnitureId =
        dalService.getPreparedStatement("SELECT  DISTINCT f.*,p.*,t.name as type_furniture "
            + "FROM project.furniture f, project.photos p, project.furniture_types t "
            + " WHERE f.furniture_id=p.furniture AND t.furniture_type_id=f.furniture_type "
            + " AND f.request_for_visit= ?  ");
    try {
      selectAllPhotoByFurnitureId.setInt(1, visitRequestId);
      try (ResultSet rs = selectAllPhotoByFurnitureId.executeQuery()) {
        Map<PhotoDTO, FurnitureDTO> mapPhotoDTOs = new HashMap<PhotoDTO, FurnitureDTO>();
        while (rs.next()) {
          FurnitureDTO furniture = furnitureDao.setAttributByResultSet(rs);
          PhotoDTO photo = setPhotoDto(rs);
          mapPhotoDTOs.put(photo, furniture);
        }
        return mapPhotoDTOs;

      } catch (Exception e) {
        throw new FatalException(e.getMessage());
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  /**
   * Select a photo whit his id.
   *
   * @param photoId the ID of the photo
   * @return the object PhotoDTO
   */
  @Override
  public PhotoDTO selectPhotoById(int photoId) {
    PreparedStatement selectPhotoById =
        dalService.getPreparedStatement("SELECT * FROM project.photos WHERE photo_id = ?");
    try {
      selectPhotoById.setInt(1, photoId);
      PhotoDTO photo = null;
      try (ResultSet rs = selectPhotoById.executeQuery()) {

        while (rs.next()) {
          photo = setPhotoDto(rs);
        }
        return photo;
      }
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }


  /**
   * Sets the photo dto.
   *
   * @param rs the rs
   * @return the photo DTO
   */
  private PhotoDTO setPhotoDto(ResultSet rs) {

    PhotoDTO photoDto = photoFactory.getPhotoDTO();
    try {
      photoDto.setPhotoId(rs.getInt("photo_id"));
      photoDto.setPhoto(rs.getString("photo"));
      photoDto.setFurniture(rs.getInt("furniture"));
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return photoDto;

  }



}
