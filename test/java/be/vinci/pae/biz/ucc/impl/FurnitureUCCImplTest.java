package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.exception.BizException;

class FurnitureUCCImplTest {

  private FurnitureFactory furnitureFactory;
  private FurnitureDAO furnitureDao;
  private FurnitureUCC furnitureUcc;
  private FurnitureDTO furnitureDto;

  @BeforeEach
  void setUp() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.furnitureFactory = locator.getService(FurnitureFactory.class);
    this.furnitureDao = locator.getService(FurnitureDAO.class);
    this.furnitureUcc = locator.getService(FurnitureUCC.class);
    this.furnitureDto =  (Furniture) FurfurnitureFactory.getFurnitureDTO();

  }

  @Test
  void testEditFurniture() {
    Mockito.when(furnitureDao.updateFurniture(furnitureDto)).thenReturn(true);
    assertAll(
        () -> assertDoesNotThrow(
            () -> furnitureUcc.editFurniture(furnitureDto, new HashSet<PhotoDTO>()),
            "method throw exception"),
        () -> assertTrue(furnitureUcc.editFurniture(furnitureDto, new HashSet<PhotoDTO>()),
            "method retrun false should return true"));
  }

  @DisplayName("testSelectFurnitureByIdShouldReturnFurniture")
  @Test
  void testSelectFurnitureByIdShouldReturnFurniture() {
    Mockito.when(furnitureDao.selectById(1)).thenReturn(furnitureDto);
    assertDoesNotThrow(() -> furnitureUcc.selectFurnitureById(1));
  }

  @DisplayName("testSelectFurnitureByIdShouldThrowException")
  @Test
  void testSelectFurnitureByIdShouldThrowException() {
    Mockito.when(furnitureDao.selectById(1)).thenReturn(null);
    assertThrows(BizException.class, () -> furnitureUcc.selectFurnitureById(1));
  }

  @DisplayName("testSelectAllFurnitureShouldReturnSetFurniture")
  @Test
  void testSelectAllFurnitureShouldReturnSetFurniture() {
    Mockito.when(furnitureDao.selectAll()).thenReturn(new HashSet<FurnitureDTO>());
    assertDoesNotThrow(() -> furnitureUcc.selectAllFurniture());
  }

  @DisplayName("testSelectAllFurnitureShouldThrowException")
  @Test
  void testSelectAllFurnitureShouldThrowException() {
    Mockito.when(furnitureDao.selectAll()).thenReturn(null);
    assertThrows(BizException.class, () -> furnitureUcc.selectAllFurniture());
  }

}
