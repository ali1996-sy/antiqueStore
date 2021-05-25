package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.HashSet;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.dal.dao.PhotoDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;

class PhotoUCCImplTest {
  private PhotoFactory photoFactory;
  private PhotoDAO photoDao;
  private PhotoUCC photoUcc;
  private PhotoDTO photoDto;
  private HashSet<PhotoDTO> setPhoto;


  @BeforeEach
  void setUp() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.photoFactory = locator.getService(PhotoFactory.class);
    this.photoDao = locator.getService(PhotoDAO.class);
    this.photoUcc = locator.getService(PhotoUCC.class);
    this.photoDto = photoFactory.getPhotoDTO();
    setPhoto = new HashSet<PhotoDTO>();
  }

  @Test
  void testSelectAllPhotoByFurnitureId() {
    Mockito.when(photoDao.selectAllPhotoByFurnitureId(1)).thenReturn(setPhoto);
    Mockito.when(photoDao.selectAllPhotoByFurnitureId(-1)).thenThrow(FatalException.class);
    assertAll(() -> assertDoesNotThrow(() -> photoUcc.selectAllPhotoByFurnitureId(1)),
        () -> assertEquals(setPhoto, photoUcc.selectAllPhotoByFurnitureId(1)),
        () -> assertThrows(BizException.class, () -> photoUcc.selectAllPhotoByFurnitureId(-1)));
  }

  @Test
  void testSelectAllPhotoByVisitRequestId() {
    HashMap<PhotoDTO, FurnitureDTO> map = new HashMap<PhotoDTO, FurnitureDTO>();
    Mockito.when(photoDao.selectAllPhotoByVisitRequestId(1)).thenReturn(map);
    Mockito.when(photoDao.selectAllPhotoByVisitRequestId(-1)).thenThrow(FatalException.class);
    assertAll(() -> assertDoesNotThrow(() -> photoUcc.selectAllPhotoByVisitRequestId(1)),
        () -> assertEquals(map, photoUcc.selectAllPhotoByVisitRequestId(1)),
        () -> assertThrows(BizException.class, () -> photoUcc.selectAllPhotoByVisitRequestId(-1)));
  }

  @Test
  void testSelectAllPhoto() {
    Mockito.when(photoDao.selectAllPhoto()).thenReturn(setPhoto);
    assertAll(() -> assertDoesNotThrow(() -> photoUcc.selectAllPhoto()),
        () -> assertEquals(setPhoto, photoUcc.selectAllPhoto()));
    Mockito.when(photoDao.selectAllPhoto()).thenThrow(FatalException.class);
    assertThrows(BizException.class, () -> photoUcc.selectAllPhoto());
  }

  @Test
  void testInsertPhoto() {
    Mockito.when(photoDao.addPhotos(1, "photoTest")).thenReturn(photoDto);
    Mockito.when(photoDao.addPhotos(-1, "photoTest")).thenThrow(FatalException.class);
    assertAll(() -> assertDoesNotThrow(() -> photoUcc.insertPhoto("photoTest", 1)),
        () -> assertEquals(photoDto, photoUcc.insertPhoto("photoTest", 1)),
        () -> assertThrows(BizException.class, () -> photoUcc.insertPhoto("photoTest", -1)));
  }

}
