package be.vinci.pae.biz.factory.impl;

import be.vinci.pae.biz.dto.VisitRequestDTO;
import be.vinci.pae.biz.factory.VisitRequestFactory;
import be.vinci.pae.biz.impl.VisitRequestImpl;

public class VisitRequestFactoryImpl implements VisitRequestFactory {


  @Override
  public VisitRequestDTO getVisitRequestDTO() {
    return new VisitRequestImpl();
  }


}
