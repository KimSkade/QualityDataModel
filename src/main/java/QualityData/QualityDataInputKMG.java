package QualityData;


import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.MultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

import java.util.ArrayList;
import java.util.List;

public class QualityDataInputKMG {
    public static AssetAdministrationShell createAASQualityDataAASfromKMG(String dateipfad, String breakpoint){
        List<SubmodelElementCollection> resultCollection = new ArrayList<>();
        List<SubmodelElementCollection> SampleDataCollection = new ArrayList<>();
        List<SubmodelElementCollection> SampleBatchCollection = new ArrayList<>();
        List<SubmodelElementCollection> ReferencesCollection = new ArrayList<>();
        List<SubmodelElementCollection> QualityFeaturesNameCollection = new ArrayList<>();

        SubmodelElementCollection Features = new SubmodelElementCollection("Features");


        int i = 1;
        while (!breakpoint.equals(DataInput.getValueFromTableByColumnAndRow(dateipfad, "planid", i))) {
            SubmodelElementCollection result = QualityData.createResultSubmodelElementCollection(
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "actual", i)),
                    12 / 01 / 2023,
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "uppertol", i)),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "lowertol", i)),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "nominal", i)));
            result.setIdShort("result" + i);
            resultCollection.add(result);

            SubmodelElementCollection SampleData = QualityData.createSampleDataSubmodelElementCollection(
                    resultCollection.get(i - 1), 12344,
                    20 / 2023, 3);
            SampleData.setIdShort("SampleData" + i);
            SampleDataCollection.add(SampleData);

            SubmodelElementCollection SampleBatch = QualityData.createSampleBatchSubmodelElementCollection(
                    SampleDataCollection.get(i - 1), 5);
            SampleBatch.setIdShort("SampleBatch" + i);
            SampleBatchCollection.add(SampleBatch);

            SubmodelElementCollection References = QualityData.createReferencesSubmodelElementCollection();
            References.setIdShort("References" + i);
            ReferencesCollection.add(References);

            SubmodelElementCollection QualityFeatureName = QualityData.createQualityFeatureNameSubmodelElementCollection
                    (SampleBatchCollection.get(i - 1),
                    DataInput.getValueFromTableByColumnAndRow(dateipfad, "featureid", i),
                    DataInput.getValueFromTableByColumnAndRow(dateipfad, "type", i),
                    DataInput.getValueFromTableByColumnAndRow(dateipfad, "unit", i),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "nominal", i)),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "uppertol", i)),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "lowertol", i)),
                    Double.parseDouble(DataInput.getValueFromTableByColumnAndRow(dateipfad, "warningLimitCF", i)),
                    1, ReferencesCollection.get(i - 1), "Equipment1");
            QualityFeatureName.setIdShort("QualityFeatureName" + i);
            QualityFeaturesNameCollection.add(QualityFeatureName);

            Features.addSubmodelElement(QualityFeaturesNameCollection.get(i-1));

            //System.out.println("vor Schleife" + QualityFeaturesNameCollection.get(i - 1).size());
            //System.out.println(QualityFeaturesNameCollection.get(i - 1));
            //System.out.println(QualityFeaturesNameCollection.get(i - 1).size());
            //System.out.println("Features SubmodelElements- Iteration " + i + ". " + Features);
            //System.out.println();

            if (breakpoint.equals(DataInput.getValueFromTableByColumnAndRow(dateipfad, "planid", i + 1))) {
                System.out.println("END");
                break;
            }
            i++;
        }
        //System.out.println("ResultCollection Size: " + resultCollection.size());
        //System.out.println(resultCollection.get(0));
        //System.out.println(resultCollection.get(1));
        //System.out.println(resultCollection.get(21));
        //System.out.println(SampleBatchCollection.get(0).getSubmodelElements().size());
        //System.out.println("Features SubmodelElements: " + Features);


        /*for (int j= 0; j< 10; j++) {
            //System.out.println("Features.size vor Schleife " + Features.getSubmodelElements().size());
            //System.out.println("vor Schleife" + QualityFeaturesNameCollection.get(j).size());
            SampleDataCollection.get(j).addSubmodelElement(resultCollection.get(j));
            SampleBatchCollection.get(j).addSubmodelElement(SampleDataCollection.get(j));
            QualityFeaturesNameCollection.get(j).addSubmodelElement(SampleBatchCollection.get(j));

            Features.addSubmodelElement(QualityFeaturesNameCollection.get(j));

            //System.out.println(QualityFeaturesNameCollection.get(j));
            //System.out.println(QualityFeaturesNameCollection.get(j).size());
            //System.out.println("Features in Schleife " + Features.getSubmodelElements());
            //System.out.println("Features.size in Schleife- Iteration " + j + ": " + Features.getSubmodelElements().size());
        }*/
        //System.out.println("SampleBatch.size nach Schleife " + SampleBatchCollection.get(12).getSubmodelElements().size());


        System.out.println("Features.size nach Schleife " + Features.getSubmodelElements().size());
        /*System.out.println("nach Schleife: " + QualityFeaturesNameCollection.get(0));
        System.out.println(QualityFeaturesNameCollection.get(0).getSubmodelElements().size());
        System.out.println(QualityFeaturesNameCollection.size());*/

        /*for (SubmodelElementCollection Qualityfeatures : QualityFeaturesNameCollection) {
            Features.addSubmodelElement(Qualityfeatures);
            //System.out.println(Features.getSubmodelElements().size());
        }*/
        //System.out.println("Features.size nach Schleife " + Features.getSubmodelElements().size());

        SubmodelElementCollection ProductionProcedures = QualityData.createProductionProceduresSubmodelElementCollection(Features);
        Submodel QualityDatas = QualityData.createQualityDataSubmodel(ProductionProcedures);
        AssetAdministrationShell QualityDataAAS = QualityData.createQualityDataAAS(QualityDatas);
        MultiSubmodelProvider fullProvider = Http.getFullProvier(QualityDataAAS, QualityDatas);
        Http.startServerWithInMemoryRegistry(fullProvider, QualityDataAAS);

        return QualityDataAAS;

    }
    public static void main(String[] args) {
        String dateipfad = "C:\\Users\\kim0_\\Desktop\\Masterarbeit\\PruefplanValidierungsbauteil1_16.txt";
        String breakpoint = "END";

        AssetAdministrationShell QualityDataAAS = createAASQualityDataAASfromKMG(dateipfad, breakpoint);






       // QualityData.createResultSubmodelElementCollection(Double.parseDouble(DataInput.getValueFromTableByColumnAndRow
        // (dateipfad, "actual", 1)), 12/01/2023, 2,2);


    }

}
