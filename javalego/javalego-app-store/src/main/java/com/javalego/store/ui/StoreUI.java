package com.javalego.store.ui;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.javalego.application.AppContext;
import com.javalego.demo.lazy.DemoDomain;
import com.javalego.demo.lazy.DemoDomainContainer;
import com.javalego.demo.lazy.ServiceDemo;
import com.javalego.exception.LocalizedException;
import com.javalego.model.keys.Icon;
import com.javalego.model.locales.LocaleEditor;
import com.javalego.security.SecurityContext;
import com.javalego.store.items.IProduct;
import com.javalego.store.items.IProject;
import com.javalego.store.items.Type;
import com.javalego.store.items.impl.Product;
import com.javalego.store.model.StoreDataServices;
import com.javalego.store.mvp.home.StorePresenter;
import com.javalego.store.mvp.home.StoreView;
import com.javalego.store.ui.components.UIFactory;
import com.javalego.store.ui.editor.IItemEditor;
import com.javalego.store.ui.icons.MenuIcons2;
import com.javalego.store.ui.locale.LocaleStore;
import com.javalego.store.ui.login.LoginScreen;
import com.javalego.store.ui.login.LoginScreen.LoginListener;
import com.javalego.ui.UIContext;
import com.javalego.ui.condition.ConditionType;
import com.javalego.ui.filter.impl.FilterParamsService;
import com.javalego.ui.mvp.beans.view.list.IListBeansViewModel;
import com.javalego.ui.mvp.beans.view.list.ListBeansViewObserver;
import com.javalego.ui.mvp.beans.view.list.ListBeansViewPresenter;
import com.javalego.ui.mvp.beans.view.list.adapter.ListBeansViewAdapter;
import com.javalego.ui.mvp.editor.bean.impl.BeanEditorObserver;
import com.javalego.ui.mvp.editor.beans.filters.EditorFiltersPresenter.EditorFilterObserver;
import com.javalego.ui.mvp.editor.filter.params.EditorFilterParamsPresenter;
import com.javalego.ui.vaadin.UIContextVaadin;
import com.javalego.ui.vaadin.component.button.ButtonExt;
import com.javalego.ui.vaadin.component.criteria.CriteriaBuilder;
import com.javalego.ui.vaadin.component.criteria.CriteriaField;
import com.javalego.ui.vaadin.component.criteria.CriteriaField.ClassField;
import com.javalego.ui.vaadin.component.criteria.CriteriaSqlTextBuilder;
import com.javalego.ui.vaadin.component.util.MessagesUtil;
import com.javalego.ui.vaadin.css.CssVaadin;
import com.javalego.ui.vaadin.icons.ResourceIconsVaadin;
import com.javalego.ui.vaadin.view.editor.AppErrorView;
import com.javalego.ui.vaadin.view.editor.beans.filters.EditorFilterParamsView;
import com.javalego.ui.vaadin.view.editor.beans.list.ICustomBeanView;
import com.javalego.ui.vaadin.view.editor.beans.list.TileBeansView;
import com.javalego.ui.vaadin.view.editor.beans.list.lazy.LazyQuery;
import com.javalego.ui.vaadin.view.editor.beans.list.lazy.TileBeansViewLazy;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Theme("vaadin_valo")
@PreserveOnRefresh
@SuppressWarnings("unused")
public class StoreUI extends UI implements ViewChangeListener {

	private static final long serialVersionUID = 7490292173903985112L;

	@Override
	protected void init(VaadinRequest request) {

		Responsive.makeResponsive(this);

		setLocale(request.getLocale());

		Page.getCurrent().setTitle(UIContext.getText(AppContext.getCurrent().getTitle()));

		if (AppContext.getCurrent().getError() != null) {
			setContent(new AppErrorView(AppContext.getCurrent().getError().getLocalizedMessage()));
		}
		else {

			// tile();
			// font();
			// criteriafilter();
			// filter();
			//list();
			 store();
			// editProduct();
			// editProject();
			// options();

			// test();
			// columnLayout();
		}

	}

	private void list() {

		DemoDomainContainer container = new DemoDomainContainer();

		ICustomBeanView<DemoDomain> c = new ICustomBeanView<DemoDomain>() {
			@Override
			public AbstractComponent getComponent(final DemoDomain bean) throws LocalizedException {
				return new Label(bean.getName());
			}
		};

		TileBeansViewLazy<DemoDomain> v = new TileBeansViewLazy<DemoDomain>(c);
		v.setNumber(14);
		v.setContainer(container);
		
		final ServiceDemo service = new ServiceDemo();

		container.setLazyQuery(new LazyQuery<DemoDomain>() {
			@Override
			public int getLazySize() {
				return service.count(false);
			}

			@Override
			public List<DemoDomain> getLazyItemsIds(int startIndex, int numberOfIds) {
				return service.getItemsFromTo(false, startIndex, numberOfIds);
			}

			@Override
			public int getLazyFilteredSize() {
				return service.countFilter(false, null);
			}

			@Override
			public List<DemoDomain> getLazyFilteredItemsIds(int startIndex, int numberOfIds) {
				return service.getFilterItemsFromTo(false, null, startIndex, numberOfIds);
			}
		});

		//v.setContainer(container);

		try {
			v.load();
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setContent(v);

	}

	private void filter() {

		FilterParamsService f = new FilterParamsService(LocaleStore.FIND);
		f.addParam("NOMBRE", LocaleStore.FAVORITES, new Boolean(false), ConditionType.EQUAL);
		EditorFilterParamsPresenter p = new EditorFilterParamsPresenter(f, new EditorFilterParamsView());
		p.setObserver(new EditorFilterObserver() {
			@Override
			public void removeCurrentFilter() throws LocalizedException {
			}

			@Override
			public void execute(String statement, String naturalStatement) throws LocalizedException {
				System.out.println(statement);
			}
		});
		try {
			p.load();
			setContent((Component) p.getView());
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void criteriafilter() {

		final Collection<CriteriaField> fields = new HashSet<CriteriaField>();
		fields.add(new CriteriaField("name", "Name"));
		fields.add(new CriteriaField("code", "Code", ClassField.INTEGER));
		fields.add(new CriteriaField("summa", "Summa", ClassField.FLOAT));
		fields.add(new CriteriaField("date", "Date", ClassField.DATE));
		fields.add(new CriteriaField("city", "City"));
		CriteriaBuilder criteriaBuilder = new CriteriaBuilder(fields);

		System.out.println("Criteria: " + CriteriaSqlTextBuilder.getSql(criteriaBuilder));

		setContent(criteriaBuilder);

		// Label l = FontAwesomeIcons.getCurrent().getComponent("caption",
		// "Twitter", FontAwesome.TWITTER);
		// l.addStyleName(CssVaadin.getMargin10());
		// l.addStyleName(CssVaadin.getBorder());
		// //setContent(l);//
		// FontAwesomeIcons.getCurrent().getComponent("caption", "Twitter",
		// FontAwesome.TWITTER));
		// setContent(new CssButton("Hola", null, FontAwesome.ANDROID,
		// false));// FontAwesomeIcons.getCurrent().getComponent("caption",
		// "Twitter", FontAwesome.TWITTER));
		//
		// FontAwesomeLabel l = new
		// FontAwesomeLabel(FontAwesomeIcon.ANDROID).setSize3x();
		// //setContent(l);
		//
		// CssLayout c = new CssLayout();
		// c.setWidth("100%");
		//
		// FormLayout controlLayout = new FormLayout();
		// controlLayout.setMargin(true);
		// controlLayout.setSpacing(true);
		//
		// controlLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		// TextField t = new TextField("Hola", "Hola");
		// t.addStyleName(ValoTheme.TEXTFIELD_HUGE);
		// controlLayout.addComponents(t, new TextField("Hola", "Hola"), new
		// TextField("Hola", "Hola"), new TextField("Hola", "Hola"), new
		// TextField("Hola", "Hola"), new TextField("Hola", "Hola"));
		//
		// c.addComponent(controlLayout);
		//
		// controlLayout = new FormLayout();
		// controlLayout.setMargin(true);
		// controlLayout.setSpacing(true);
		//
		// controlLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		// t = new TextField("Hola", "Hola");
		// t.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT);
		// controlLayout.addComponents(t, new TextField("2222", "Ho2222la"), new
		// TextField("Hola", "Hola"), new TextField("Hola", "Hola"), new
		// TextField("Hola", "Hola"), new TextField("Hola", "Hola"));
		// //t.addStyleName(ValoTheme.TEXTFIELD_HUGE);
		//
		// c.addComponent(controlLayout);
		//
		// setContent(c); //new
		// Label("<span class='v-icon' style='font-family: FontAwesome;'>&#xf17b;</span>",
		// ContentMode.HTML));

		// HorizontalLayout title = new HorizontalLayout();/* {
		// @Override
		// protected String getCss(Component c) {
		// return "display: inline-block;";
		// }
		// };*/
		// title.addStyleName(CssVaadin.getBorder());
		// title.setWidth("100%");
		//
		// CssLayout t = new CssLayout();
		// Label l = new
		// Label("Hola22 213 2134 324 34324 234 RRR 21321 321 324 3243 2312 3",
		// ContentMode.HTML);
		// //l.setIcon(RepositoryIconsVaadin.getCurrent().getResource(IconsBlack32.MEMBER));
		// t.addComponent(l);
		// Button b = new Button("Hola 3");
		// b.setHeight("100%");
		// title.addComponent(b);
		// title.addComponent(t);
		// title.setComponentAlignment(t, Alignment.TOP_LEFT);
		// setContent(title);

	}

	private void tile() {

		CssLayout content = new CssLayout();
		content.addStyleName(CssVaadin.getShadow());
		content.setWidth("100%");

		HorizontalLayout title = new HorizontalLayout();
		title.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		title.setWidth("100%");
		// FontAwesomeLabel ll = FontAwesome.ANDROID.getLabel().setSize3x();
		// ll.addStyleName("colored");

		try {
			Component i = UIContextVaadin.getComponent(MenuIcons2.RULES);
			i.addStyleName(CssVaadin.getMargin4());
			CssLayout l = new CssLayout();
			l.addStyleName("menupanels-level2-red");
			l.addComponent(i);
			l.setHeight("100%");
			title.addComponent(l);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Label l = new Label(
				"<font size=+1><strong>Componente Vaadin asdf asdfd fsd fsda</strong></font><br>Detalle del componente para describirlo asdf sdaf sadfsd afsdDe Roberto RAnz Clemente</br>",
				ContentMode.HTML);
		l.addStyleName(CssVaadin.getMargin4());
		title.addComponent(l);
		title.setExpandRatio(l, 1);
		content.addComponent(title);

		setContent(content);

	}

	// private void font() {
	//
	// VerticalLayout content = new VerticalLayout();
	// content.setSpacing(true);
	// content.setMargin(true);
	// content.addComponent(FontAwesome.ANDROID.getLabel());
	//
	// Label l2 = new Label("AASDFASDFASDFASDF");
	// l2.addStyleName("robotofont");
	// content.addComponent(l2);
	//
	// TextField t = new TextField();
	//
	// t.setCaption("Hola");
	// t.setIcon(EuroFontIcon.EURO);
	// content.addComponent(t);
	//
	// // 3x Large Spinning Cog!
	// content.addComponent(FontAwesome.APPLE.getLabel().setSize3x().spin());
	//
	// // Stack icons
	// content.addComponent(FontAwesome.TWITTER.getLabel().stack(FontAwesome.SQUARE_O).setSize3x());
	//
	// // Stack icons with separate modifiers
	// Label l =
	// FontAwesome.HDD_O.getLabel().stack(FontAwesome.EXCLAMATION.getLabel().inverseColor()).setSize3x().reverseStackSize();
	// l.setCaption("Hola");
	// content.addComponent(l);
	//
	// // Set the icon of another Vaadin component
	// Button b = new Button();
	// b.setIcon(FontAwesome.SMILE_O);
	// content.addComponent(b);
	//
	// setContent(content);
	//
	// }

	// private void columnLayout() {
	//
	// ColumnLayout l = new ColumnLayout();
	//
	// l.addComponent(new TextField(), 0);
	// l.addComponent(new TextField(), 1);
	// l.addComponent(new TextField(), 2);
	//
	// setContent(l);
	// }

	@SuppressWarnings({ "unchecked" })
	private void editProduct() {

		IProduct p = null;
		try {
			StoreDataServices ds = StoreAppContext.getCurrent().getDataServices();
			
			p = ds.newInstanceProduct(ds.newInstanceProject(Type.ARCHITECTURE));
		}
		catch (LocalizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IItemEditor<Product> detail = (IItemEditor<Product>) UIFactory.getEditor(p, false, false);
		try {
			detail.load();
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContent(detail);
	}

	@SuppressWarnings({ "unchecked" })
	private void editProject() {

		IProject p = null;
		try {
			StoreDataServices ds = StoreAppContext.getCurrent().getDataServices();

			p = ds.newInstanceProject(Type.ARCHITECTURE);
		}
		catch (LocalizedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IItemEditor<IProject> detail = (IItemEditor<IProject>) UIFactory.getEditor(p, false, false);
		try {
			detail.load();
		}
		catch (LocalizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setContent(detail);
	}

	/**
	 * Test
	 */
	private void test() {

		final CssLayout l = new CssLayout();

		// Adaptador para visualizar los datos del bean
		ListBeansViewAdapter<Product> adapter = new ListBeansViewAdapter<Product>() {
			@Override
			public String toHtml(Product bean) {
				return bean.toHtml();
			}

			@Override
			public Icon getIcon(Product bean) {
				return bean.getIcon();
			}
		};

		// Modelo de datos
		IListBeansViewModel<Product> model = new IListBeansViewModel<Product>() {

			@Override
			public Class<Product> getBeanClass() {
				return Product.class;
			}

			@Override
			public Collection<Product> getBeans(String filter, String order) throws LocalizedException {
				return StoreAppContext.getCurrent().getDataServices().getAllProducts();
			}
		};

		final TileBeansView<Product> view = new TileBeansView<Product>(adapter);

		final ListBeansViewPresenter<Product> presenter = new ListBeansViewPresenter<Product>(model, view);

		presenter.setObserver(new ListBeansViewObserver<Product>() {
			@SuppressWarnings({ "unchecked" })
			@Override
			public void editBean(Product bean) throws LocalizedException {

				// // Editor del item
				IItemEditor<Product> detail = (IItemEditor<Product>) UIFactory.getEditor(bean, isReadOnly(), true);

				// Notificación de cambios
				detail.setObserver(new BeanEditorObserver<Product>() {
					@Override
					public void commit(Product bean) throws LocalizedException {
						view.update(bean);
						setContent(l);
					}

					@Override
					public void discard(Product bean) {
						setContent(l);
					}

					@Override
					public void remove(Product bean) throws LocalizedException {
						view.removeBean(bean);
						setContent(l);
					}
				});

				detail.load();
				detail.setWidth("100%");
				setContent(detail);
			}
		});

		try {
			presenter.load();

			ButtonExt b = new ButtonExt(LocaleEditor.INSERT).blue();
			b.addStyleName(CssVaadin.getMargin4());
			b.addClickListener(new ClickListener() {
				private static final long serialVersionUID = -2073980638454411532L;

				@SuppressWarnings("unchecked")
				@Override
				public void buttonClick(ClickEvent event) {

					IProduct bean = null;
					try {
						StoreDataServices ds = StoreAppContext.getCurrent().getDataServices();

						bean = ds.newInstanceProduct(ds.newInstanceProject(Type.ARCHITECTURE));
					}
					catch (LocalizedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Editor del item
					IItemEditor<Product> detail = (IItemEditor<Product>) UIFactory.getEditor(bean, isReadOnly(), false);

					// Notificación de cambios
					detail.setObserver(new BeanEditorObserver<Product>() {
						@Override
						public void commit(Product bean) throws LocalizedException {
							view.insert(bean);
							setContent(l);
						}

						@Override
						public void discard(Product bean) {
							setContent(l);
						}

						@Override
						public void remove(Product bean) throws LocalizedException {
							setContent(l);
						}
					});
					try {
						detail.load();
					}
					catch (LocalizedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					detail.setWidth("100%");

					setContent(detail);
				}
			});

			l.addComponent(view);
			l.addComponent(b);

			setContent(l);
		}
		catch (LocalizedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Cargar UI store.
	 */
	@SuppressWarnings({ "serial" })
	private void store() {

		if (!SecurityContext.getCurrent().getServices().isAuthenticated()) {

			try {
				setContent(new LoginScreen(new LoginListener() {
					@Override
					public void loginSuccessful() {
						showMainView();
					}
				}));
			}
			catch (LocalizedException e) {
				MessagesUtil.error(e);
			}
		}
		else {
			showMainView();
		}
	}

	private void showMainView() {
		try {
			new StorePresenter(new StoreView()).load();
			getNavigator().navigateTo(getNavigator().getState());
		}
		catch (LocalizedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {

		// boolean isAuthenticated =
		// SecurityManager.getCurrent().isAuthenticated();
		//
		// if (isAuthenticated &&
		// MeccanoLoginView.NAME.equals(event.getViewName())) {
		// getNavigator().navigateTo(MeccanoView.NAME);
		// return false;
		// }
		// else if (!isAuthenticated &&
		// !MeccanoLoginView.NAME.equals(event.getViewName())) {
		// getNavigator().navigateTo(MeccanoLoginView.NAME);
		// return false;
		// }

		return true;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
