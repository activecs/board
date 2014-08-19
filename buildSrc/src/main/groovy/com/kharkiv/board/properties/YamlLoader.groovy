import org.yaml.snakeyaml.Yaml

class YamlLoader {
	final String YAML_FILE = 'properties.yaml';
	final String PROPERTIES_FILE = './src/main/resources/application.properties';
	
	String env;
	Yaml propertiesYaml;
	Properties envProps;
	
	YamlLoader(String env){
		this.env = env;
		this.propertiesYaml = new Yaml();
	}
	
	void process(){
		loadProps();
		generatePropFile();
	}
	
	void loadProps(){
		InputStream streamYamlProperties = new FileInputStream(new File(YAML_FILE));
		def props = propertiesYaml.load(streamYamlProperties);
		envProps = props['properties'][env];
	}
	void generatePropFile(){
		File propsfile = new File(PROPERTIES_FILE);
		propsfile.delete();
		envProps.each{k,v->  propsfile << "${k}=${v}\n"}
	}
}