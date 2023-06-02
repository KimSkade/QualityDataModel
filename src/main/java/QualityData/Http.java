package QualityData;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistry;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.registration.restapi.AASRegistryModelProvider;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.restapi.SubmodelProvider;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.util.List;

public class Http {
    private static final Logger logger = LoggerFactory.getLogger(Http.class);
    public static MultiSubmodelProvider getFullProvier(AssetAdministrationShell QualityDataShell,
                                                       Submodel QualityData) {
        // create aas model provider for all submodels
        MultiSubmodelProvider fullProvider = new MultiSubmodelProvider();

        AASModelProvider aasProvider = new AASModelProvider(QualityDataShell);
        fullProvider.setAssetAdministrationShell(aasProvider);

        SubmodelProvider submodelProvider = new SubmodelProvider(QualityData);
        fullProvider.addSubmodel(submodelProvider);

        return fullProvider;
    }

    public static void startServerWithInMemoryRegistry(MultiSubmodelProvider fullProvider,
                                                       AssetAdministrationShell QualityDataShell) {
        // create aas server
        HttpServlet aasServlet = new VABHTTPInterface<IModelProvider>(fullProvider);
        logger.info("Created a servlet for the model");

        // create registry with provider and servlet
        IAASRegistry registry = new InMemoryRegistry();
        IModelProvider registryProvider = new AASRegistryModelProvider(registry);
        HttpServlet registryServlet = new VABHTTPInterface<IModelProvider>(registryProvider);
        logger.info("Created a registry servlet for the model");

        // Register the VAB model at the directory
        AASDescriptor aasDescriptor = new AASDescriptor(QualityDataShell, "http://localhost:4009/aasserver/shells/QualityDataAAS/aas");
        registry.register(aasDescriptor);

        // Deploy the AAS on a HTTP server
        BaSyxContext context = new BaSyxContext("/aasserver", "", "localhost", 4009);
        context.addServletMapping("/shells/QualityDataAAS/*", aasServlet);
        context.addServletMapping("/registry/*", registryServlet);
        BaSyxHTTPServer httpServer = new BaSyxHTTPServer(context);

        httpServer.start();
        logger.info("HTTP server started");
    }
}
