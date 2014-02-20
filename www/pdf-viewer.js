var pdfViewerPlugin = {
    openpdf: function(file, label, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'PdfViewerPlugin', // mapped to our native Java class called "PdfViewerPlugin"
            'openpdf', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "file": file,
                "label": label
            }]
        ); 
     }
}
module.exports = pdfViewerPlugin;