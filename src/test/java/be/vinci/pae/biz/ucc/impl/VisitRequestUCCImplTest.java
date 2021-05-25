package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.Deque;

import be.vinci.pae.biz.dto.AddressDTO;
import be.vinci.pae.biz.dto.UserDTO;
import be.vinci.pae.biz.dto.VisitRequestDTO;
import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.factory.VisitRequestFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.VisitRequestUCC;
import be.vinci.pae.dal.dao.UserDAO;
import be.vinci.pae.dal.dao.VisitRequestDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;

class VisitRequestUCCImplTest {
  private VisitRequestFactory visitRequestFactory;
  private VisitRequestUCC visitRequestUcc;
  private VisitRequestDAO visitRequestDao;
  private UserDAO userDao;
  private VisitRequestDTO visitRequestDto;
  private UserDTO userDto;
  private UserFactory userFactory;
  private AddressDTO addressDto;
  private AddressDTO addressDtoFalse;
  private AddressFactory addressFactory;
  private VisitRequestDTO visitRequestDtoFalse;

  @BeforeEach
  void setUp() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.visitRequestFactory = locator.getService(VisitRequestFactory.class);
    this.userFactory = locator.getService(UserFactory.class);
    this.addressFactory = locator.getService(AddressFactory.class);
    this.visitRequestDao = locator.getService(VisitRequestDAO.class);
    this.userDao = locator.getService(UserDAO.class);
    this.visitRequestUcc = locator.getService(VisitRequestUCC.class);
    visitRequestDto = visitRequestFactory.getVisitRequestDTO();
    visitRequestDtoFalse = visitRequestFactory.getVisitRequestDTO();
    addressDtoFalse = addressFactory.getAddressDTO();
    userDto = userFactory.getUserDTO();
    userDto.setId(1);
    addressDto = addressFactory.getAddressDTO();
    addressDto.setId(1);
    addressDtoFalse.setId(-1);
    userDto.setAddressDTO(addressDto);
    visitRequestDto.setSeller(userDto);
    visitRequestDtoFalse.setSeller(userDto);
    visitRequestDto.setFurnitureAddress(addressDto);
    visitRequestDtoFalse.setFurnitureAddress(addressDtoFalse);
    visitRequestDto.setTimeSlot("lundi soir");
    visitRequestDtoFalse.setTimeSlot("lundi soir");
    visitRequestDto.setCancellationsDue("test");
    visitRequestDtoFalse.setCancellationsDue("test");
    visitRequestDtoFalse.setVisitId(2);
    visitRequestDto.setChosenDate("20-04-2021");
    visitRequestDtoFalse.setChosenDate("20-04-2021");
  }

  @Test
  void testCreateVisitRequest() {
    Mockito.when(visitRequestDao.creatVisitRequest(1, "lundi soir", 1)).thenReturn(1);
    Mockito.when(userDao.selectUserByID(1)).thenReturn(userDto);
    visitRequestDtoFalse.getFurnitureAddress().setId(-2);
    Mockito.when(visitRequestDao.creatVisitRequest(-2, "lundi soir", 1))
        .thenThrow(FatalException.class);
    assertAll(
        () -> assertDoesNotThrow(() -> visitRequestUcc.createVisitRequest(visitRequestDto),
            "method shouldn't throw an  exception "),
        () -> assertEquals(1, visitRequestUcc.createVisitRequest(visitRequestDto)));
    visitRequestDtoFalse.getSeller().setAddressDTO(addressDtoFalse);
    assertThrows(BizException.class,
        () -> visitRequestUcc.createVisitRequest(visitRequestDtoFalse));
    Mockito.when(visitRequestDao.creatVisitRequest(1, "lundi soir", 1)).thenReturn(-1);
    assertThrows(BizException.class,
        () -> visitRequestUcc.createVisitRequest(visitRequestDtoFalse));
  }



  @Test
  void testCancelVisitRequest() {
    Mockito.when(visitRequestDao.cancelVisitRequest(1, "test")).thenReturn(true);
    Mockito.when(visitRequestDao.cancelVisitRequest(2, "test")).thenThrow(FatalException.class);
    assertAll(() -> {
      assertDoesNotThrow(() -> visitRequestUcc.cancelVisitRequest(visitRequestDto));
    }, () -> assertTrue(visitRequestUcc.cancelVisitRequest(visitRequestDto)),
        () -> assertThrows(BizException.class,
            () -> visitRequestUcc.cancelVisitRequest(visitRequestDtoFalse)));
  }

  @Test
  void testConfirmVisitRequest() {
    Mockito.when(visitRequestDao.confirmVisitRequest(1, "20-04-2021", false)).thenReturn(true);
    Mockito.when(visitRequestDao.confirmVisitRequest(2, "20-04-2021", false))
        .thenThrow(FatalException.class);
    assertAll(() -> {
      assertDoesNotThrow(() -> visitRequestUcc.confirmVisitRequest(visitRequestDto));
    }, () -> assertTrue(visitRequestUcc.cancelVisitRequest(visitRequestDto)),
        () -> assertThrows(BizException.class,
            () -> visitRequestUcc.confirmVisitRequest(visitRequestDtoFalse)));
  }

  @Test
  void testSelectAlltVisitRequest() {
    Deque<VisitRequestDTO> list = new ArrayDeque<VisitRequestDTO>();
    Mockito.when(visitRequestDao.selectAllVisitRequest()).thenReturn(list);
    assertAll(() -> {
      assertDoesNotThrow(() -> {
        visitRequestUcc.selectAlltVisitRequest();
      }, "method shouldn't throw an  exception ");
    }, () -> assertEquals(list, visitRequestUcc.selectAlltVisitRequest()));
    Mockito.when(visitRequestDao.selectAllVisitRequest()).thenThrow(FatalException.class);
    assertThrows(BizException.class, () -> visitRequestUcc.selectAlltVisitRequest());
  }

  @Test
  void testSelectVisitRequestById() {
    Deque<VisitRequestDTO> list = new ArrayDeque<VisitRequestDTO>();
    Mockito.when(visitRequestDao.selectVisitRequestById(1)).thenReturn(list);
    Mockito.when(visitRequestDao.selectVisitRequestById(-1)).thenThrow(FatalException.class);
    assertAll(() -> assertDoesNotThrow(() -> visitRequestUcc.selectVisitRequestById(1)),
        () -> assertEquals(list, visitRequestUcc.selectVisitRequestById(1)),
        () -> assertThrows(BizException.class, () -> visitRequestUcc.selectVisitRequestById(-1)));
  }

}
