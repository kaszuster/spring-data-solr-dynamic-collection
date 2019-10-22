package de.dev86.app;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "#{@collectionName}")
public class Order {

    @Id
    @Indexed
    private String id;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
