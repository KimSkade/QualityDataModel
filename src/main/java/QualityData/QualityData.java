package QualityData;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QualityData {
    public static void addPropertiesToResult(SubmodelElementCollection result, List<Property> properties) {
        for (Property property : properties) {
            result.addSubmodelElement(property);
        }
    }


    public static SubmodelElementCollection createResultSubmodelElementCollection
            (double value, Object measuredate, double tolerance, double targetValue) {  //ToDo: Datenformat measuredate
        //Toleranz & TargetValue muss hier für Resultcheck übergeben werden

        boolean resultcheck;

        resultcheck = (targetValue - tolerance) <= value && value <= (targetValue + tolerance);

        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Value", value));
        properties.add(new Property("MeasureDate", measuredate));
        properties.add(new Property("ResultCheck", resultcheck));

        SubmodelElementCollection result = new SubmodelElementCollection("result");

        for (Property property : properties) {
            result.addSubmodelElement(property);
        }
        return result;
    }

    public static SubmodelElementCollection createSampleDataSubmodelElementCollection
            (SubmodelElementCollection result, int sampleNumber, Object sampledate, int partcounter) {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("SampleNumber", sampleNumber));
        properties.add(new Property("SampleDate", sampledate));
        properties.add(new Property("PartCounter", partcounter));

        SubmodelElementCollection SampleData = new SubmodelElementCollection("SampleData");

        for (Property property : properties) {
            SampleData.addSubmodelElement(property);
        }

        SampleData.addSubmodelElement(result);

        return SampleData;
    }

    public static SubmodelElementCollection createSampleBatchSubmodelElementCollection
            (SubmodelElementCollection SampleData, int sampleSize) {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("SampleSize", sampleSize));

        SubmodelElementCollection SampleBatch = new SubmodelElementCollection("SampleBatch");

        for (Property property : properties) {
            SampleBatch.addSubmodelElement(property);
        }

        SampleBatch.addSubmodelElement(SampleData);

        return SampleBatch;
    }

    public static SubmodelElementCollection createQualityFeatureNameSubmodelElementCollection
            (SubmodelElementCollection SampleBatch, String featureType, String function, String unit, double targetValue,
             double tolerance, double warningLimit, double controlLimit, SubmodelElementCollection References,
             String inspectionEquipment) {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("FeatureType", featureType));
        properties.add(new Property("Function", function));
        properties.add(new Property("Unit", unit));
        properties.add(new Property("TargetValue", targetValue));
        properties.add(new Property("Tolerance", tolerance));
        properties.add(new Property("WaringLimit", warningLimit));
        properties.add(new Property("ControlLimit", controlLimit));
        properties.add(new Property("InspectionEquipment", inspectionEquipment));

        SubmodelElementCollection QualityFeatureName = new SubmodelElementCollection("QualityFeatureName");

        for (Property property : properties) {
            QualityFeatureName.addSubmodelElement(property);
        }

        QualityFeatureName.addSubmodelElement(References);
        QualityFeatureName.addSubmodelElement(SampleBatch);

        return QualityFeatureName;
    }

    public static SubmodelElementCollection createReferencesSubmodelElementCollection() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Point", "PlatzhalterPoint"));
        properties.add(new Property("Line", "PlatzhalterLine"));
        properties.add(new Property("Surface", "PlatzhalterSurface"));
        properties.add(new Property("Axis", "PlatzhalterAxis"));

        SubmodelElementCollection References = new SubmodelElementCollection("References");

        for (Property property : properties) {
            References.addSubmodelElement(property);
        }
        return References;
    }


    public static SubmodelElementCollection createFeaturesSubmodelElementCollection
    (SubmodelElementCollection QualityFeaturesName) {
        SubmodelElementCollection Features = new SubmodelElementCollection("Features");
        Features.addSubmodelElement(QualityFeaturesName);
        return Features;
    }



    public static SubmodelElementCollection createProductionProceduresSubmodelElementCollection
            (SubmodelElementCollection Features) {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("Ressource", "PlatzhalterRessource"));
        properties.add(new Property("Process", "PlatzhalterProcess"));

        SubmodelElementCollection ProductionProcedures = new SubmodelElementCollection("ProductionProcedures");

        for (Property property : properties) {
            ProductionProcedures.addSubmodelElement(property);
        }

        ProductionProcedures.addSubmodelElement(Features);

        return ProductionProcedures;
    }

    public static final Asset QualityDataAsset = new Asset("QualityData", new CustomId("Quality Asset"),
            AssetKind.INSTANCE);

    public static Submodel createQualityDataSubmodel(SubmodelElementCollection ProductionProcedures) {
        Submodel QualityData = new Submodel();
        QualityData.addSubmodelElement(ProductionProcedures);
        return QualityData;
    }

    public static AssetAdministrationShell createQualityDataAAS(Submodel QualityData) {
        AssetAdministrationShell QualityDataShell = new AssetAdministrationShell("QualityDataShell",
                new Identifier(), QualityDataAsset);
        QualityDataShell.addSubmodel(QualityData);
        return QualityDataShell;
    }

    public static void main(String[] args) {
        SubmodelElementCollection result = createResultSubmodelElementCollection(10, 20 / 2021,
                1, 10);
        SubmodelElementCollection SampleData = createSampleDataSubmodelElementCollection(result, 12344,
                20 / 2023, 3);
        SubmodelElementCollection SampleBatch = createSampleBatchSubmodelElementCollection(SampleData, 5);
        SubmodelElementCollection References = createReferencesSubmodelElementCollection();
        SubmodelElementCollection QualityFeatureName = createQualityFeatureNameSubmodelElementCollection(SampleBatch,
                "Test", "Test", "mm", 10,1, 1, 1,
                References, "Equipment1");
        SubmodelElementCollection Features = createFeaturesSubmodelElementCollection(QualityFeatureName);
        SubmodelElementCollection ProductionProcedures = createProductionProceduresSubmodelElementCollection(Features);
        Submodel QualityData = createQualityDataSubmodel(ProductionProcedures);
        AssetAdministrationShell QualityDataAAS = createQualityDataAAS(QualityData);

        MultiSubmodelProvider fullProvider = Http.getFullProvier(QualityDataAAS, QualityData);
        Http.startServerWithInMemoryRegistry(fullProvider, QualityDataAAS);

    }


}
