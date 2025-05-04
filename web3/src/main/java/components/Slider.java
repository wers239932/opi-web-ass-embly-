package components;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.*;

@FacesComponent("com.piromant.jsf.Slider")
public class Slider extends UIInput implements ClientBehaviorHolder {

    private static final String DEFAULT_MIN = "0";
    private static final String DEFAULT_MAX = "100";
    private static final String DEFAULT_STEP = "1";
    private static final List<String> EVENT_NAMES = List.of("change", "slideEnd");

    @Override
    public String getFamily() {
        return "com.piromant.jsf.SliderComponent";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = getClientId(context);

        Double sliderValue = (Double) getValue();
        if (sliderValue == null) {
            sliderValue = Double.valueOf(getMin());
        }

        writer.startElement("div", this);
        writer.writeAttribute("id", clientId, "id");
        if (getStyleClass() != null) {
            writer.writeAttribute("class", getStyleClass(), "styleClass");
        }
        if (getStyle() != null) {
            writer.writeAttribute("style", getStyle(), "style");
        }
        writer.startElement("input", this);
        writer.writeAttribute("type", "range", null);
        writer.writeAttribute("id", clientId + "_slider", null);
        writer.writeAttribute("name", clientId, null);
        writer.writeAttribute("value", sliderValue, "value");
        writer.writeAttribute("min", getMin(), "min");
        writer.writeAttribute("max", getMax(), "max");
        writer.writeAttribute("step", getStep(), "step");
        writer.writeAttribute("step", getStep(), "step");
        Map<String,List<ClientBehavior>> behaviors = getClientBehaviors();
        ClientBehaviorContext behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, this, "change", getClientId(context), null);
        if (behaviors.containsKey("change") ) {
            String click = behaviors.get("change").get(0).getScript(behaviorContext);
            writer.writeAttribute("onchange", click, null);
        }
        behaviorContext = ClientBehaviorContext.createClientBehaviorContext(context, this, "slideEnd", getClientId(context), null);
        if (behaviors.containsKey("slideEnd")) {
            String slideEndScript = behaviors.get("slideEnd").get(0).getScript(behaviorContext);
            writer.writeAttribute("onmouseup", slideEndScript, null);
        }
        writer.endElement("input");

        writer.endElement("div");
    }

    @Override
    public void decode(FacesContext context) {
        String clientId = getClientId(context);
        String newValue = context.getExternalContext().getRequestParameterMap().get(clientId);
        if (newValue != null) {
            try {
                double doubleValue = Double.parseDouble(newValue);
                setValue(doubleValue);
            } catch (NumberFormatException e) {
                sendBadRequest();
            }
        }
        super.decode(context);
    }



    @Override
    public void setValue(Object value) {
        double doubleValue = (Double) value;
        double max = Double.parseDouble(this.getMax());
        double min = Double.parseDouble(this.getMin());
        double step = Double.parseDouble(this.getStep());
        if (doubleValue > max || doubleValue < min
                || (doubleValue != min && (doubleValue - min) / step - (int) ((doubleValue - min) / step) != 0)) {
            sendBadRequest();
        }
        super.setValue(value);
    }

    public String getStyleClass() {
        return (String) getStateHelper().eval("styleClass");
    }

    public void setStyleClass(String styleClass) {
        getStateHelper().put("styleClass", styleClass);
    }

    public String getStyle() {
        return (String) getStateHelper().eval("style");
    }

    public void setStyle(String style) {
        getStateHelper().put("style", style);
    }

    public String getMin() {
        return (String) getStateHelper().eval("min", DEFAULT_MIN);
    }

    public void setMin(String min) {
        getStateHelper().put("min", min);
    }

    public String getMax() {
        return (String) getStateHelper().eval("max", DEFAULT_MAX);
    }

    public void setMax(String max) {
        getStateHelper().put("max", max);
    }

    public String getStep() {
        return (String) getStateHelper().eval("step", DEFAULT_STEP);
    }

    public void setStep(String step) {
        getStateHelper().put("step", step);
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    private void sendBadRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().setResponseStatus(400);
        try {
            context.getExternalContext().getResponseOutputWriter().write("Значение должно числом быть в диапазоне от " + getMin() + " до " + getMax() + " с шагом " + getStep());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        context.responseComplete();
    }


}
