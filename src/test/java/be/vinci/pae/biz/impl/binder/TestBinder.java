package be.vinci.pae.biz.impl.binder;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

import be.vinci.pae.biz.factory.AddressFactory;
import be.vinci.pae.biz.factory.FurnitureFactory;
import be.vinci.pae.biz.factory.OptionFactory;
import be.vinci.pae.biz.factory.PhotoFactory;
import be.vinci.pae.biz.factory.UserFactory;
import be.vinci.pae.biz.factory.VisitRequestFactory;
import be.vinci.pae.biz.factory.impl.AddressFactoryImpl;
import be.vinci.pae.biz.factory.impl.FurnitureFactoryImpl;
import be.vinci.pae.biz.factory.impl.OptionFactoryImpl;
import be.vinci.pae.biz.factory.impl.PhotoFactoryImpl;
import be.vinci.pae.biz.factory.impl.UserFactoryImpl;
import be.vinci.pae.biz.factory.impl.VisitRequestFactoryImpl;
import be.vinci.pae.biz.ucc.FurnitureUCC;
import be.vinci.pae.biz.ucc.OptionUCC;
import be.vinci.pae.biz.ucc.PhotoUCC;
import be.vinci.pae.biz.ucc.UserUCC;
import be.vinci.pae.biz.ucc.VisitRequestUCC;
import be.vinci.pae.biz.ucc.impl.FurnitureUCCImpl;
import be.vinci.pae.biz.ucc.impl.OptionUCCImpl;
import be.vinci.pae.biz.ucc.impl.PhotoUCCImpl;
import be.vinci.pae.biz.ucc.impl.UserUCCImpl;
import be.vinci.pae.biz.ucc.impl.VisitRequestUCCImpl;
import be.vinci.pae.dal.DalServices;
import be.vinci.pae.dal.dao.FurnitureDAO;
import be.vinci.pae.dal.dao.OptionDAO;
import be.vinci.pae.dal.dao.PhotoDAO;
import be.vinci.pae.dal.dao.UserDAO;
import be.vinci.pae.dal.dao.VisitRequestDAO;
import be.vinci.pae.dal.impl.DalServicesImpl;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;


@Provider
public class TestBinder extends AbstractBinder {
  /**
   * Configure.
   */
  @Override
  protected void configure() {

    bind(VisitRequestFactoryImpl.class).to(VisitRequestFactory.class).in(Singleton.class);
    bind(OptionFactoryImpl.class).to(OptionFactory.class).in(Singleton.class);
    bind(PhotoFactoryImpl.class).to(PhotoFactory.class).in(Singleton.class);
    bind(AddressFactoryImpl.class).to(AddressFactory.class).in(Singleton.class);
    bind(UserFactoryImpl.class).to(UserFactory.class).in(Singleton.class);
    bind(FurnitureFactoryImpl.class).to(FurnitureFactory.class).in(Singleton.class);
    bind(PhotoFactoryImpl.class).to(PhotoFactory.class).in(Singleton.class);
    bind(Mockito.mock(DalServicesImpl.class)).to(DalServices.class);
    bind(Mockito.mock(UserDAO.class)).to(UserDAO.class);
    bind(Mockito.mock(FurnitureDAO.class)).to(FurnitureDAO.class);
    bind(Mockito.mock(PhotoDAO.class)).to(PhotoDAO.class);
    bind(Mockito.mock(VisitRequestDAO.class)).to(VisitRequestDAO.class);
    bind(Mockito.mock(PhotoDAO.class)).to(PhotoDAO.class);
    bind(Mockito.mock(OptionDAO.class)).to(OptionDAO.class);
    bind(VisitRequestUCCImpl.class).to(VisitRequestUCC.class).in(Singleton.class);
    bind(OptionUCCImpl.class).to(OptionUCC.class).in(Singleton.class);
    bind(PhotoUCCImpl.class).to(PhotoUCC.class).in(Singleton.class);
    bind(UserUCCImpl.class).to(UserUCC.class).in(Singleton.class);
    bind(FurnitureUCCImpl.class).to(FurnitureUCC.class).in(Singleton.class);

  }
}

