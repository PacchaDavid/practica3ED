const baseURL = 'http://localhost:5000/'
const aLabel = document.getElementById('searchRequest')
const inputS = document.getElementById('inputS')


function buildSearchUrl(className,attribute) {
    const searchURL = `${baseURL}${className}/search`
    aLabel.setAttribute('href',`${searchURL}/${attribute}/`)
}

function searchUtility(className) {
    if (aLabel.href != `${baseURL}${className}/table`) {
        aLabel.href += inputS.value
    }
    if(inputS.value != '')
        aLabel.click()
}


const aLabelSrt = document.getElementById('sortRequest')
const inputSrt = document.getElementById('inputSrt')


function buildSortUrl(className,attribute) {
    const searchURL = `${baseURL}${className}/sort`
    aLabelSrt.setAttribute('href',`${searchURL}/${attribute}/`)
}

function typeSort(value) {
    aLabelSrt += value;
}

function sortUtility(className,order) {
    if (aLabelSrt.href != `${baseURL}${className}/table`) {
        aLabelSrt.href += order + '/'
        aLabelSrt.href += inputSrt.value
    }
    console.log(aLabelSrt.href)
    aLabelSrt.click()
}