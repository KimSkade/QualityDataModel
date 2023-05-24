package New_ProcessModel;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.CustomId;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

public class QualityData {
    public static void main(String[] args) {

        // Assets generieren und AAS instanziieren
        Asset QualityDataAsset = new Asset("QualityData", new CustomId("Quality Asset"), AssetKind.INSTANCE);
        AssetAdministrationShell QualityDataShell = new AssetAdministrationShell("QualityDataShell",
                new Identifier(), QualityDataAsset);

        Submodel QualityData = new Submodel();


        Procedure Prozedur1 = new Procedure("Id Prozedur 1", "Das ist die Prozedur 1", )
        List<Procedure> Procedures = new List();

        addProceduretoSubmodel(QualityData, Procedures);


    }

    public static void addProcedurestoAAS(Submodel QualityData, List<Procedures> Procedures) {

        for(i = 0, ){

            SubmodelElementCollection Prozedur1 = new SubmodelElementCollection(Infos aus den Proceduren);

            QualityData.addSubmodelElement(Prozedur1);

        }

    }


}
