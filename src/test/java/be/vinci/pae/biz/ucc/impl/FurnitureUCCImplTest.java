package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.HashSet;

import be.vinci.pae.biz.dto.FurnitureDTO;
import be.vinci.pae.biz.dto.PhotoDTO;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;

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
    this.furnitureDto = furnitureFactory.getFurnitureDTO();

  }

  @DisplayName("testEditFurnitureShouldReturnTrue")
  @Test
  void testEditFurnitureShouldReturnTrue() {
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
    Mockito.when(furnitureDao.selectById(2)).thenReturn(furnitureDto);
    assertDoesNotThrow(() -> furnitureUcc.selectFurnitureById(2));
  }



  @DisplayName("testSelectAllFurnitureShouldReturnSetFurniture")
  @Test
  void testSelectAllFurnitureShouldReturnSetFurniture() {
    Mockito.when(furnitureDao.selectAll()).thenReturn(new ArrayDeque<FurnitureDTO>());
    assertDoesNotThrow(() -> furnitureUcc.selectAllFurniture());
  }



  @DisplayName("testchangeFurnitureStateShouldThrowException")
  @Test
  void testchangeFurnitureStateShouldThrowException() {
    Mockito.when(furnitureDao.changeFurnitureState(-1, "for_sale"))
        .thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class,
        () -> furnitureUcc.changeFurnitureState(-1, "for_sale"));
  }

  @DisplayName("testchangeFurnitureStateShouldReturnTrue")
  @Test
  void testchangeFurnitureStateShouldReturnTrue() {
    Mockito.when(furnitureDao.changeFurnitureState(1, "for_sale")).thenReturn(true);
    assertTrue(furnitureUcc.changeFurnitureState(1, "for_sale"));
  }

  @DisplayName("testSelectAllFurnitureByStateShouldReturnlistFurniture")
  @Test
  void testSelectAllFurnitureByStateShouldReturnlistFurniture() {
    ArrayDeque<FurnitureDTO> deque = new ArrayDeque<FurnitureDTO>();
    deque.add(furnitureDto);
    Mockito.when(furnitureDao.selectAllByState("for_sale")).thenReturn(deque);
    assertEquals(furnitureUcc.selectAllFurnitureByState("for_sale"), deque);
  }

  @DisplayName("testSelectAllFurnitureByStateShouldThrowException")
  @Test
  void testSelectAllFurnitureByStateShouldThrowException() {

    Mockito.when(furnitureDao.selectAllByState("to_sale")).thenThrow(FatalException.class);
    assertThrows(BizException.class, () -> furnitureUcc.selectAllFurnitureByState("to_sale"));
  }



  @DisplayName("testdynamicFurnitureSearchShouldReturnListFurniture")
  @Test
  void testdynamicFurnitureSearchShouldReturnListFurniture() {
    HashSet<String> set = new HashSet<String>();
    ArrayDeque<FurnitureDTO> deque = new ArrayDeque<FurnitureDTO>();
    deque.add(furnitureDto);
    Mockito.when(furnitureDao.selectDynamicSearch("test", 0, 300, set)).thenReturn(deque);
    assertEquals(furnitureUcc.dynamicFurnitureSearch("test", 0, 300, set), deque);
  }


}
