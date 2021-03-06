package org.iplantc.de.pipelines.client.views;

import org.iplantc.de.client.models.pipelines.Pipeline;
import org.iplantc.de.client.models.pipelines.PipelineApp;
import org.iplantc.de.resources.client.messages.I18N;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorDelegate;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;

import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import java.util.List;

/**
 * An implementation of the PipelineAppOrderView.
 *
 * @author psarando
 *
 */
public class PipelineAppOrderViewImpl implements PipelineAppOrderView, Editor<Pipeline> {

    @UiTemplate("PipelineAppOrderView.ui.xml")
    interface PipelineAppOrderUiBinder extends UiBinder<Widget, PipelineAppOrderViewImpl> {
    }

    private static PipelineAppOrderUiBinder uiBinder = GWT.create(PipelineAppOrderUiBinder.class);
    private static PipelineAppProperties pipelineAppProps = GWT.create(PipelineAppProperties.class);
    private final Widget widget;
    private Presenter presenter;

    ListStoreEditor<PipelineApp> apps;

    public PipelineAppOrderViewImpl() {
        widget = uiBinder.createAndBindUi(this);

        apps = new AppListStoreEditor(this);
        appOrderGrid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @UiField
    Grid<PipelineApp> appOrderGrid;

    @UiField
    ListStore<PipelineApp> pipelineAppStore;

    @UiFactory
    ListStore<PipelineApp> createListStore() {
        ListStore<PipelineApp> store = new ListStore<PipelineApp>(new ModelKeyProvider<PipelineApp>() {

            @Override
            public String getKey(PipelineApp item) {
                return presenter.getStepName(item);
            }
        });

        store.addSortInfo(new StoreSortInfo<PipelineApp>(pipelineAppProps.step(), SortDir.ASC));

        return store;
    }

    @UiFactory
    ColumnModel<PipelineApp> createColumnModel() {
        return new AppColumnModel(pipelineAppProps);
    }

    @UiHandler("addAppsBtn")
    public void onAddAppsClick(SelectEvent e) {
        presenter.onAddAppsClicked();
    }

    @UiHandler("removeAppBtn")
    public void onRemoveAppClick(SelectEvent e) {
        presenter.onRemoveAppClicked();
    }

    @UiHandler("moveUpBtn")
    public void onMoveUpClick(SelectEvent e) {
        presenter.onMoveUpClicked();
    }

    @UiHandler("moveDownBtn")
    public void onMoveDownClick(SelectEvent e) {
        presenter.onMoveDownClicked();
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ListStore<PipelineApp> getPipelineAppStore() {
        return pipelineAppStore;
    }

    @Override
    public PipelineApp getOrderGridSelectedApp() {
        return appOrderGrid.getSelectionModel().getSelectedItem();
    }

    public class AppListStoreEditor extends ListStoreEditor<PipelineApp> {
        private final PipelineAppOrderView view;
        private EditorDelegate<List<PipelineApp>> delegate;

        public AppListStoreEditor(PipelineAppOrderView view) {
            super(view.getPipelineAppStore());

            this.view = view;
        }

        @Override
        public void flush() {
            ListStore<PipelineApp> pipelineAppStore = view.getPipelineAppStore();
            if (delegate != null && pipelineAppStore.size() < 2) {
                delegate.recordError(I18N.DISPLAY.selectOrderPnlTip(), pipelineAppStore.getAll(), view);
            }

            super.flush();
        }

        @Override
        public void onPropertyChange(String... paths) {
            // no-op
        }

        @Override
        public void setDelegate(EditorDelegate<List<PipelineApp>> delegate) {
            this.delegate = delegate;
        }
    }
}
