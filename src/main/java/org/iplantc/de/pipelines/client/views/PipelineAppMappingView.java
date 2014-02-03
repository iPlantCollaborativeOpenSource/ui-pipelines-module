package org.iplantc.de.pipelines.client.views;

import java.util.List;

import org.iplantc.de.pipelineBuilder.client.json.autobeans.PipelineApp;

import com.google.gwt.editor.client.ValueAwareEditor;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * A View for displaying and editing Pipeline Output to Input mappings.
 * 
 * @author psarando
 * 
 */
public interface PipelineAppMappingView extends IsWidget, ValueAwareEditor<List<PipelineApp>> {

    public interface Presenter {
        /**
         * Sets a mapping for targetStep's Input DataObject, with the given targetInputId, to
         * sourceStep's Output DataObject with the given sourceOutputId. A null sourceOutputId will clear
         * the mapping for the given targetInputId.
         * 
         * @param targetStep
         * @param targetInputId
         * @param sourceStep
         * @param sourceOutputId
         */
        public void setInputOutputMapping(PipelineApp targetStep, String targetInputId,
                PipelineApp sourceStep, String sourceOutputId);

        public boolean isMappingValid(PipelineApp targetStep);
    }

    public void setPresenter(Presenter presenter);

    public void clearInvalid();
}
