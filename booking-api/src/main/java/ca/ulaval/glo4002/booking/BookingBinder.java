package ca.ulaval.glo4002.booking;

import ca.ulaval.glo4002.booking.artists.*;
import ca.ulaval.glo4002.booking.configuration.Configuration;
import ca.ulaval.glo4002.booking.configuration.ConfigurationController;
import ca.ulaval.glo4002.booking.events.*;
import ca.ulaval.glo4002.booking.exceptions.ExceptionMapper;
import ca.ulaval.glo4002.booking.orders.*;
import ca.ulaval.glo4002.booking.oxygen.*;
import ca.ulaval.glo4002.booking.oxygen.history.InMemoryOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.oxygen.history.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.oxygen.inventory.InMemoryOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventoryService;
import ca.ulaval.glo4002.booking.oxygen.report.OxygenReportMapper;
import ca.ulaval.glo4002.booking.oxygen.report.OxygenReportService;
import ca.ulaval.glo4002.booking.passes.bundles.PassBundleFactory;
import ca.ulaval.glo4002.booking.passes.bundles.PassBundleMapper;
import ca.ulaval.glo4002.booking.passes.PassFactory;
import ca.ulaval.glo4002.booking.profits.ProfitMapper;
import ca.ulaval.glo4002.booking.profits.ProfitService;
import ca.ulaval.glo4002.booking.program.ProgramController;
import ca.ulaval.glo4002.booking.program.ProgramService;
import ca.ulaval.glo4002.booking.report.ReportController;
import ca.ulaval.glo4002.booking.shuttles.*;
import ca.ulaval.glo4002.booking.numbers.NumberGenerator;
import ca.ulaval.glo4002.booking.shuttles.manifest.ShuttleManifestController;
import ca.ulaval.glo4002.booking.shuttles.manifest.ShuttleManifestMapper;
import ca.ulaval.glo4002.booking.shuttles.manifest.ShuttleManifestService;
import ca.ulaval.glo4002.booking.shuttles.trips.InMemoryTripRepository;
import ca.ulaval.glo4002.booking.shuttles.trips.TripMapper;
import ca.ulaval.glo4002.booking.shuttles.trips.TripRepository;
import ca.ulaval.glo4002.booking.shuttles.trips.TripService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.graalvm.compiler.nodes.memory.MemoryCheckpoint;

import javax.inject.Singleton;

public class BookingBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindConfiguration();
        bindGenerators();
        bindFactories();
        bindRepositories();
        bindProducers();
        bindServices();
        bindMappers();
        bindControllers();
        bindConverters();
        bindClients();
    }

    private void bindConfiguration() {
        bindAsContract(Configuration.class).in(Singleton.class);
    }

    private void bindGenerators() {
        bindAsContract(NumberGenerator.class);
    }
    
    private void bindConverters() {
    	bindAsContract(ArtistConverter.class);
    }
    
    private void bindClients() {
    	bindAsContract(ArtistClient.class);
    }

    private void bindFactories() {
        bindAsContract(PassFactory.class);
        bindAsContract(PassBundleFactory.class);
        bindAsContract(OxygenFactory.class);
        bindAsContract(ShuttleFactory.class);
        bindAsContract(OrderFactory.class);
        bindAsContract(EventFactory.class);
        bindAsContract(EventDateFactory.class);
    }

    private void bindRepositories() {
        bind(InMemoryOxygenInventoryRepository.class).to(OxygenInventoryRepository.class).in(Singleton.class);
        bind(InMemoryOxygenHistoryRepository.class).to(OxygenHistoryRepository.class).in(Singleton.class);
        bind(InMemoryTripRepository.class).to(TripRepository.class).in(Singleton.class);
        bind(InMemoryOrderRepository.class).to(OrderRepository.class).in(Singleton.class);
        bind(InMemoryArtistRepository.class).to(ArtistRepository.class).in(Singleton.class);
        bind(InMemoryEventRepository.class).to(EventRepository.class).in(Singleton.class);
    }

    private void bindProducers() {
        bindAsContract(OxygenTankProducer.class);
    }

    private void bindServices() {
        bindAsContract(OxygenInventoryService.class);
        bindAsContract(TripService.class);
        bindAsContract(OrderService.class);
        bindAsContract(ShuttleManifestService.class);
        bindAsContract(ArtistService.class);
        bindAsContract(ProfitService.class);
        bindAsContract(ProgramService.class);
        bindAsContract(OxygenReportService.class);
        bindAsContract(EventDateService.class);
    }

    private void bindMappers() {
        bindAsContract(ExceptionMapper.class);
        bindAsContract(PassBundleMapper.class);
        bindAsContract(OrderMapper.class);
        bindAsContract(TripMapper.class);
        bindAsContract(ShuttleManifestMapper.class);
        bindAsContract(OxygenInventoryMapper.class);
        bindAsContract(OxygenHistoryMapper.class);
        bindAsContract(OxygenReportMapper.class);
        bindAsContract(ProfitMapper.class);
    }

    private void bindControllers() {
        bindAsContract(ProgramController.class).in(Singleton.class);
        bindAsContract(OrderController.class).in(Singleton.class);
        bindAsContract(ShuttleManifestController.class).in(Singleton.class);
        bindAsContract(ReportController.class).in(Singleton.class);
        bindAsContract(ConfigurationController.class).in(Singleton.class);
    }
}
