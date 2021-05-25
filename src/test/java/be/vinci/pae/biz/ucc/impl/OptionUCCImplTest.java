package be.vinci.pae.biz.ucc.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import be.vinci.pae.biz.dto.OptionDTO;
import be.vinci.pae.biz.dto.OptionDTO.State;
import be.vinci.pae.biz.factory.OptionFactory;
import be.vinci.pae.biz.impl.binder.TestBinder;
import be.vinci.pae.biz.ucc.OptionUCC;
import be.vinci.pae.dal.dao.OptionDAO;
import be.vinci.pae.exception.BizException;
import be.vinci.pae.exception.FatalException;

class OptionUCCImplTest {
  private OptionFactory optionFactory;
  private OptionDAO optionDao;
  private OptionUCC optionUcc;
  private OptionDTO optionDto;
  private OptionDTO optionDtoFalse;


  @BeforeEach
  void setUp() throws Exception {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.optionFactory = locator.getService(OptionFactory.class);
    this.optionDao = locator.getService(OptionDAO.class);
    this.optionUcc = locator.getService(OptionUCC.class);
    this.optionDto = optionFactory.getOptionDTO();
    this.optionDtoFalse = optionFactory.getOptionDTO();
    optionDto.setBuyer(1);
    optionDto.setFurniture(1);
    optionDto.setState("pending");
    optionDtoFalse.setBuyer(1);
    optionDtoFalse.setFurniture(1);
    optionDtoFalse.setState("");
  }

  @Test
  void testPutInOption() {
    Mockito.when(optionDao.insertOption(optionDto)).thenReturn(1);
    assertAll(() -> assertThrows(BizException.class, () -> optionUcc.putInOption(optionDtoFalse)),
        () -> assertDoesNotThrow(() -> optionUcc.putInOption(optionDto)),
        () -> assertTrue(optionUcc.putInOption(optionDto)));
    optionDtoFalse.setState("pending");
    Mockito.when(optionDao.insertOption(optionDtoFalse)).thenThrow(FatalException.class);
    assertThrows(BizException.class, () -> optionUcc.putInOption(optionDtoFalse));

  }

  @Test
  void testCancelOption() {
    Mockito.when(optionDao.changeOptionState(1, State.CANCELLED.getValue())).thenReturn(true);
    Mockito.when(optionDao.changeOptionState(2, State.CANCELLED.getValue()))
        .thenThrow(FatalException.class);
    assertAll(
        () -> assertThrows(BizException.class,
            () -> optionUcc.cancelOption(2, State.CANCELLED.getValue())),
        () -> assertDoesNotThrow(() -> optionUcc.cancelOption(1, State.CANCELLED.getValue())),
        () -> assertTrue(optionUcc.cancelOption(1, State.CANCELLED.getValue())));

  }

  @Test
  void testSelectAllOption() {
    Mockito.when(optionDao.selectAll()).thenReturn(new ArrayList<OptionDTO>());
    assertAll(() -> assertDoesNotThrow(() -> optionUcc.selectAllOption()),
        () -> assertNotNull(optionUcc.selectAllOption()));
    Mockito.when(optionDao.selectAll()).thenReturn(null);
    assertAll(() -> assertThrows(BizException.class, () -> optionUcc.selectAllOption()));
    Mockito.when(optionDao.selectAll()).thenThrow(FatalException.class);
    assertAll(() -> assertThrows(BizException.class, () -> optionUcc.selectAllOption()));
  }

}
