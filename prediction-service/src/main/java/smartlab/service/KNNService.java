package smartlab.service;

import org.springframework.stereotype.Service;
import smartlab.model.Preference;
import smartlab.model.Vote;
import smartlab.model.UserTemperatureProfile;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class KNNService {

    private Attribute attrExternalTemperature = new Attribute("externalTemperatura");
    private Attribute attrInternalTemperature = new Attribute("internalTemperature");
    private Attribute attrUsers = new Attribute("users");
    private Attribute attrHour = new Attribute("hour");
    private Attribute attrPref = new Attribute("pref", Arrays.asList(
            new String[]{"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"}));

    private ArrayList<Attribute> attributes = new ArrayList<Attribute>() {{
        add(attrExternalTemperature);
        add(attrInternalTemperature);
        add(attrUsers);
        add(attrHour);
        add(attrPref);
    }};



    private Instance makeInstance(Preference preference){
        Instance instance = new DenseInstance(5);
        instance.setValue(attrExternalTemperature, preference.getExternalTemperature());
        instance.setValue(attrInternalTemperature, preference.getInternalTemperature());
        instance.setValue(attrUsers, preference.getOnlineUsers());
        instance.setValue(attrHour, preference.getHour());
        instance.setValue(attrPref, preference.getVote().toString());
        return instance;
    }

    private Classifier buildModel(List<Preference> preferences) throws Exception {

        Instances train = new Instances("dados", attributes, preferences.size());
        train.setClassIndex(4);

        List<Instance> instances = new ArrayList<>();

        preferences.stream()
                .map(this::makeInstance)
                .forEach(instances::add);

        train.addAll(instances);

        Classifier ibk = new IBk(1);
        ibk.buildClassifier(train);

        return ibk;
    }

    private List<Vote> getRatings(Classifier classifier, Instance instance) throws Exception {
        List<Vote> temperatures = new ArrayList<>();
        double[] values = classifier.distributionForInstance(instance);

        for (int i = 0; i < values.length; i++) {
            temperatures.add(new Vote(attrPref.value(i), values[i]));
        }
        return temperatures;
    }

    public UserTemperatureProfile calculateTemperatureProfile(List<Preference> preferenceList, Preference current) throws Exception {
        UserTemperatureProfile userTemperatureProfile = new UserTemperatureProfile();

        Classifier classifier = buildModel(preferenceList);
        Instance currentInstance = makeInstance(current);
        userTemperatureProfile.setVotes(getRatings(classifier, currentInstance));

        return userTemperatureProfile;
    }

}