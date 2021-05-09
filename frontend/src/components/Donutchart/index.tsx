import axios from "axios";
import Chart from "react-apexcharts";
import { SaleSum } from "Types/sale";
import { BASE_URL } from "utils/requests";

type CharData = {
  lables: string[];
  series: number[];
};

const DonutChart = () => {
  // FORMA ERRADA
  let charData: CharData = { lables: [], series: [] };

  // FORMA ERRADA
  axios.get(`${BASE_URL}/sales/amount-by-seller`).then((response) => {
    const data = response.data as SaleSum[];
    const myLabels = data.map((obj) => obj.sellerName);
    const mySeries = data.map((obj) => obj.sum);

    charData = { lables: myLabels, series: mySeries };
    console.log(charData);
  });

  //const mockData = {
  // series: [477138, 499928, 444867, 220426, 473088],
  // labels: ["Anakin", "Barry Allen", "Kal-El", "Logan", "Padmé"],
  //};

  const options = {
    legend: {
      show: true,
    },
  };

  return (
    <Chart
      options={{ ...options, labels: charData.lables }}
      series={charData.series}
      type="donut"
      height="240"
    />
  );
};

export default DonutChart;
