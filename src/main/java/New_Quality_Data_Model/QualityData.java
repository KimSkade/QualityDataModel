package New_Quality_Data_Model;

import java.util.List;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import java.util.ArrayList;

import ProcessProcedure.*;

public class QualityData extends Procedure{
    public static void main(String[] args) {

        // Assets generieren und AAS instanziieren
        Asset QualityDataAsset = new Asset("QualityData", new CustomId("Quality Asset"), AssetKind.INSTANCE);
        AssetAdministrationShell QualityDataShell = new AssetAdministrationShell("QualityDataShell",
                new Identifier(), QualityDataAsset);

        Submodel QualityData = new Submodel();

        Procedure Prozedur1 = new Procedure("Id Prozedur 1", "Das ist die Prozedur 1");
        List<Procedure> Procedures = new ArrayList<>();
        Procedures.add(Prozedur1);

        SubmodelElementCollection Features = new SubmodelElementCollection("Features");
        Prozedur1.addSubmodelElement(Features);

        List<SubmodelElementCollection> QualityFeatures = new ArrayList<>();
        SubmodelElementCollection QualityFeature1 = new SubmodelElementCollection("QualityFeature1");
        QualityFeatures.add(QualityFeature1);
        Property FeatureType = new Property("FeatureType", "Das ist der Feature Type");
        Property Function = new Property("FunctionType", "Das ist die Function");
        Property UnitType = new Property("UnitType", "Das ist die Unit Type");
        Property TargetValue = new Property("TargetValue", "Das ist der Target Value");
        Property Tolerance = new Property("Tolerance", "Das ist die Tolerance");
        Property WarningLimit = new Property("WarningLimit", "Das ist das Warning Limit1");
        Property ControlLimit = new Property("ControlLimit", "Das ist das Control Limit");
        Property InspectionEquipement = new Property("InspectionEquipement", "Das ist das Inspection Equipement");

    }

    public static void addProcedurestoAAS(Submodel QualityData, List<Procedure> Procedures) {
            for(Procedure Prozedur : Procedures){
                SubmodelElementCollection ProzedurCollection = new SubmodelElementCollection(); //was wird SubmodelElementCollection in () übergeben?
                // Process und Resource als Property hinzufügen
                QualityData.addSubmodelElement(ProzedurCollection);
        }
    }

    public static void addQualityFeaturestoAAS(Submodel QualityData, SubmodelElementCollection Features, List<SubmodelElementCollection> QualityFeatures,
                                               Property FeatureType, Property Function, Property UnitType, Property TargetValue, Property Tolerance,
                                               Property WarningLimit, Property ControlLimit, Property InspectionEquipement){
        for(SubmodelElementCollection QualityFeature : QualityFeatures){
            QualityFeauture.addSubmodelElement(FeatureType, Function, UnitType, TargetValue, Tolerance, WarningLimit, ControlLimit, InspectionEquipement);
            Features.addSubmodelElement(QualityFeature);
        }
    }

}

