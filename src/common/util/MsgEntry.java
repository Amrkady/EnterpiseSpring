package common.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.component.inputmask.InputMask;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

import com.sun.faces.component.visit.FullVisitContext;

public class MsgEntry {

	public static final String SUCCESS = "تمّت العملية بنجاح";
	public static final String SUCCESS_DELETE_MENU = "تم حذف القائمة بنجاح";
	public static final String SUCCESS_ADD_SUBMENU = "تمت إضافة القائمة الفرعية بنجاح";
	public static final String SUCCESS_SAVE = "تم الحفظ بنجاح";
	public static final String SUCCESS_SAVE_USER = "تم حفظ المستخدم بنجاح";
	public static final String SUCCESS_SAVE_ADMINISTRATION = "تم حفظ الادارة بنجاح";
	public static final String SUCCESS_SAVE_DEPARTMENT = "تم حفظ القسم بنجاح الي الادارة  : ";
	public static final String SUCCESS_SEND_GENERALIZATION = "تم إرسال التعميم بنجاح";
	public static final String SUCCESS_SAVE_VACATION = "تم حفظ الاجازة  : ";
	public static final String SUCCESS_SAVE_CONTRACTOR = "تم حفظ الوظيفة بنجاح  : ";
	public static final String SUCCESS_DELETE_VACATION = "تم حذف الاجازة  : ";
	public static final String SUCCESS_UPDATE_VACATION="تم تعديل الاجازة بنجاح";
	public static final String SUCCESS_SAVE_TIMETABLESHIFT = "تم حفظ الدوام الجديد  : ";
	public static final String SUCCESS_UPDATE_TIMETABLESHIFT = "تم تعديل الدوام   : ";
	public static final String SUCCESS_SAVE_EMPLOYER = "تم حفظ الموظف الجديد  : ";
	public static final String SUCCESS_UPDATE_EMPLOYER = "تم تعديل الموظف   : ";



	
	public static final String MSG_SELECT_DEPARTMENT = "لقد تم تحديد القسم";
	public static final String GENERALIZATION = "تعميم";

	public static final String ERROR = "لم تتمّ العملية";
	public static final String COMPLETE_LOGIN_DATA = "أكمل بيانات الدخول أولا";
	public static final String ERROR_LOGIN = "اسم المستخدم أو كلمة المرور غير صحيحة ..تأكد و أعد المحاولة";
	public static final String ERROR_PWD = "لا يمكن الاستمرار . يجب إدخال كلمة مرور صحيحة";
	public static final String ERROR_CHOOSE_EMPLOYEE = "أختر الموظف أولا";
	public static final String ERROR_CHOOSE_USER = "يجب أختيار المستخدم";
	public static final String ERROR_FAVORITE = "جهة المفضلة الجديده مطلوبة";
	public static final String ERROR_SELECT_MENU = "قم بتحديد القائمة أولا";
	public static final String ERROR_SAVE_USER = "خطأ في حفظ المستخدم";
	public static final String ERROR_SELECT_DEPARTMENT = "يجب تحديد القسم أولا";
	public static final String ERROR_DEPARTMENT_NAME = "اسم القسم الجديد مطلوب";
	public static final String ERROR_SELECT_USER = "المستخدم غير محدد لا يمكن التعديل";
	public static final String ERROR_CORRESPONDANCE = "لا يمكن تطابق مصدر و مستقبل المعاملات .";
	public static final String ERROR_SELECT_OPERATION = "اختر الاجراء المطلوب مع تحديد الموظف";
	public static final String ERROR_SEND_OPERATION = "لا يمكن ارسال المعاملة للموظف المتوقف المعاملة عنده";
	public static final String ERROR_MISSING_SPEECH = "لا يوجد خطاب .. يرجى اختيار الاجراء الصحيح";
	public static final String ERROR_MISSING_KEY = "لا يمكن الاستمرار : مفتاح البحث مفقود";
	public static final String ERROR_INVALID_DATE = "التاريخ المدخل غير صحيح";
	public static final String ERROR_INVALID_AUTHORIZATION_TIME = "وقت المغادرة يجب أن يكون قبل وقت العودة";
	public static final String ERROR_ECART_AUTHORIZATION_TIME = "الحد المسموح به للإستئذان هو ساعتان";
	public static final String ERROR_AUTHORIZATION_NB = "لقد تجاوزت الحد المسموح به للإستئذان";
	public static final String ERROR_VACATION_PERIOD = "مدة الإجازة يجب أن لا تقل عن 5 أيام";
	public static final String ERROR_INFERIOR_VACATION_DAYS = "رصيدك للإجازات لا يكفي";
	public static final String ERROR_INFERIOR_FIVE_DAYS = "أيام الإجازة يجب أن تتجاوز 5 أيام";
	public static final String ERROR_BEFORE_LAST_DAY = "تاريخ الإجازة يجب أن يتجاوز تاريخ البارحة";
	public static final String acceptMsg = Utils.loadMessagesFromFile("accept.transaction");
	public static final String refuseMsg = Utils.loadMessagesFromFile("refuse.transaction");
	public static final String ERRORE_DELETE_VACATION = "لايمكن حذف اجازة تمت الموافقة عليها";
	public static final String COMPLETEFIELD="برجاء اكتمال بقيه الحقول";
	public static final String EMPLOYERFOUND="الموظف موجود مسبقا";
	public static final String NATIONALNOFOUND="السجل المدنى غير موجود";
	public static final String SUCCESS_DELETE = "تم الحذف السجل بنجاح";
	public static final String  ERROR_CATCODE_EMPLOYER = "يجب اختيار التصنيف   : ";
	public static final String ERROR_ENTER_YEAR = null;
	public static final String SUCCESS_CANCEL_EMPLOYER= "تم الغاء العملية  : ";
	
	public static void addInfoMessage(String message) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addAcceptFlashInfoMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, acceptMsg, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	public static void addAcceptFlashInfoMessage(String msg) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
		FacesContext.getCurrentInstance().addMessage(null, message);

	}
	public static void addRefuseFlashInfoMessage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		Flash flash = facesContext.getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, refuseMsg, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public static void addErrorMessage(String message) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void showModalDialog(String Msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ø±Ø³Ø§Ù„Ø©", Msg);
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void addErrorMessageToComponent(String componentId, String message) {
		FacesContext context = FacesContext.getCurrentInstance();

		// context.getViewRoot().findComponent("myText")
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
		context.addMessage(componentId, msg);
		// context.addMessage(component.getClientId(), new FacesMessage("Test
		// msg"));
		UIComponent myComp = context.getViewRoot().findComponent(componentId);
		if (myComp instanceof InputText)
			((InputText) myComp).setValid(false);
		else if (myComp instanceof SelectOneMenu)
			((SelectOneMenu) myComp).setValid(false);
	}

	public static void addErrorAspectToComponent(String componentId) {
		// UIViewRoot viewRoot =
		// FacesContext.getCurrentInstance().getViewRoot();
		UIComponent myComp = findComponent(componentId);// viewRoot.findComponent(componentId);

		// FacesContext context = FacesContext.getCurrentInstance();
		// UIComponent myComp =
		// context.getViewRoot().findComponent(componentId);
		if (myComp instanceof InputText)
			((InputText) myComp).setValid(false);
		else if (myComp instanceof SelectOneMenu)
			((SelectOneMenu) myComp).setValid(false);
		else if (myComp instanceof InputMask)
			((InputMask) myComp).setValid(false);
	}

	public static UIComponent findComponent(final String id) {

		FacesContext context = FacesContext.getCurrentInstance();
		UIViewRoot root = context.getViewRoot();
		final UIComponent[] found = new UIComponent[1];

		root.visitTree(new FullVisitContext(context), new VisitCallback() {
			@Override
			public VisitResult visit(VisitContext context, UIComponent component) {

				if (component.getId().equals(id)) {
					found[0] = component;
					return VisitResult.COMPLETE;
				}
				return VisitResult.ACCEPT;
			}
		});

		return found[0];

	}

}
