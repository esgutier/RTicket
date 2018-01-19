var $input = $('#btn_hora').clockpicker({
    /**
     * Define el valor por defecto para la hora mostrada 
     * en el popover. Puede recibir como valor 'now' que 
     * lo configura automáticamente a la hora actual, o 
     * asignarle directamente una hora, ejemplo: '12:30'.
     * 
     * @param string 
     */
    default: 'now',
     
    /**
     * Define la posición del popover en relación al input
     * y puede recibir cualquiera de los siguientes valores:
     * -----------------------------------
     * 'top' | 'right' | 'bottom' | 'left'
     * -----------------------------------
     * 
     * @param string 
     */
    placement: 'bottom',
     
    /**
     * Define la posición de la flecha del popover y puede
     * recibir cualquiera de los siguientes valores:
     * -----------------------------------
     * 'top' | 'right' | 'bottom' | 'left'
     * -----------------------------------
     * 
     * @param string 
     */
    align: 'left',
     
    /**
     * Es el texto que se muestra en el botón para finalizar
     * con la selección de la hora. El botón no se muestra 
     * si la opción "autoclose" es true.
     * 
     * @param string 
     */
    donetext: 'Listo',
     
    /**
     * Determina si el popover debe cerrarse automáticamente
     * al terminar con la selección.
     * 
     * @param bool 
     */
    autoclose: false,
     
    /**
     * Si el valor es verdadero, cuando el popover se abra 
     * en un dispositivo móvil, éste vibrará al arrastrar 
     * el selector con los dedos.
     * 
     * @param bool 
     */
    vibrate: true,
     
    /**
     * Se asigna un valor en mili-segundos que se le añade a
     * la hora a mostrar por defecto en el popover, y solo
     * funciona si la opción 'default' es igual a 'now'.
     * 
     * @param number 
     */
    fromnow: 0,
     
    /**
     * Se ejecuta cuando el plugin es iniciado.
     * 
     * @param function 
     */
    init: function () { console.log('iniciado') },
     
    /**
     * Se ejecuta antes mostrar el popover.
     * 
     * @param function 
     */
    beforeShow: function () { console.log('antes de mostrarse') },
     
    /**
     * Se ejecuta después de mostrar el popover.
     * 
     * @param function 
     */
    afterShow: function () { console.log('después de mostrarse') },
     
    /** 
     * Se ejecuta antes de ocultar el popover.
     * 
     * @param function
     */
    beforeHide: function () { console.log('antes de ocultarse') },
     
    /**
     * Se ejecuta después de ocultar el popover
     * 
     * @param function 
     */
    afterHide: function () { console.log('después de ocultarse') },
     
    /**
     * Se ejecuta antes de establecer la hora.
     * 
     * @param function 
     */
    beforeHourSelect: function () { console.log('antes de seleccionar la hora') },
     
    /**
     * Se ejecuta después de establecer la hora.
     * 
     * @param function 
     */
    afterHourSelect: function () { console.log('después de seleccionar la hora') },
     
    /**
     * Se ejecuta ántes de completar la selección.
     * 
     * @param function 
     */
    beforeDone: function () { console.log('antes de finalizar') },
     
    /**
     * Se ejecuta después de completar la selección. 
     */
    afterDone: function () { console.log('después de finalizar') }
});